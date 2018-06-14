# -*- coding: utf-8 -*-
"""
Created on Tue Jun  5 07:18:43 2018

@author: Jane Wu
"""

import paho.mqtt.client as mqtt
import numpy as np
import re

###########  define functions ############

## function of 讀取/新增歷史紀錄 
def init(): 
    global history_1
    #讀檔
    try:  
        print("load file: success")
        history_1 = np.load('record1.npy')
        #print('history record: ', history_1)
    
    #沒有檔案就新創一個
    except EnvironmentError:
        print("No file, create new one")
        history_1 = np.zeros((24*6)) 
        
    ##print(history_1) ###for testing
 
## function of 資料分析
def data_ana():
    # 上傳sensor分析結果 拿sensor1為範例
    # ON/OFF #
    global history_1
    
    ## 先儲存歷史資料
    np.save('record1', history_1)
    ## 判斷規則(過去24小時範例) 超過一半時間球網有震動就說Occupied(占用)  若否則說Free
    if np.average(history_1)>=0.5:
        S1_str = 'Occupied'
    else: 
        S1_str = 'Free'
    
    ## 將判斷結果上傳至mqtt指定topic
    client.publish("GIOT-GW/UL/1C497B43217A/CourtProj3/local_host_S1", S1_str);
    print('剛剛上傳了一筆資料到GIOT-GW/UL/1C497B43217A/CourtProj3/local_host_S1: ' + S1_str)

## function of 連上mqtt
def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("GIOT-GW/UL/1C497B43217A") #放訂閱主題名

## function of 收到資料處理
def on_message(client, userdata, msg):
    # 收到的sensor封包格式  
    # C1: 資料1;C2: 資料2;C3: 資料3; 
    #須配合下方code來解出訊息
    global history_1
    text = msg.payload.decode()
    print('收到的資料為: ' + text) ## for checking
    
    ### 解sensor 1 資料
    try:
        # 找關鍵字判斷sensor ID
        recog = re.search('\"macAddr\":\"0000000012345672\"', text).group()
        print("找到一筆來自Sensor ID 0000000012345672 的資料")
        #history_1 = np.append(C1, history_1[0:-1]);
        #np.save('record1', history_1)
    except AttributeError:
    # 找不到指定字串
        C1 = '' # apply your error handling
        print("非來自Sensor ID 0000000012345672 的資料")
        
    #try:
    #    C1 = int(re.search('C1: (.+?);', text).group(1))
    #    print("找到一筆C1: ", C1)
    #    history_1 = np.append(C1, history_1[0:-1]);
    #    np.save('record1', history_1)
    #except AttributeError:
    ## 找不到指定字串
    #    C1 = '' # apply your error handling
    #    #print("沒找到C1")
        
    #client.disconnect()  #斷線
    print('目前的歷史紀錄: ', history_1)
    
    # 經過以上程序更新歷史紀錄後進入資料分析
    data_ana()


###########  call functions ############
    
history_1 = np.zeros((24*6)) #一個sensor過去24小時*每小時6個10分鐘的歷史數據
  
init()  #讀取/新增歷史紀錄
    
client = mqtt.Client()
client.connect("167.99.224.125",1883,60) #連結IP port，每60秒重新連線一次 by loop_forever

client.on_connect = on_connect  # 設定連結func
client.on_message = on_message  # 設定收到訊息處理func

client.loop_forever()