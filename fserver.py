import flask
import os
import numpy as np 
import pandas as pd 
import pickle
import matplotlib.pyplot as plt 
from PIL import Image
import sys
import cv2
from keras.models import load_model
from keras import  backend as K
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dense, Activation, Dropout
from tensorflow.keras.utils import to_categorical

model = load_model("finalized_model")
server = flask.Flask(__name__)
@server.route('/', methods=['GET', 'POST'])

def image_to_feature_vector(image, size=(28, 28)):
    return cv2.resize(image, size)

def handle_request():
    capturedImage = flask.request.files['image']
    predictedResult=str(getClass(capturedImage))
    capturedImage = flask.request.files['image1']
    savePath="./"+predictedResult+"/"
    savePathExists = os.path.exists(savePath)
    if not savePathExists:
        os.makedirs(savePath)
    capturedImage.save(savePath+capturedImage.filename)
    return "Success"
    
def getClass(img):
    img = Image.open(img)
    img = np.asarray(img)
    img_array = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    img_array = np.expand_dims(img_array, 2)
    size=(14, 14)
    img_array=cv2.resize(img_array, size)
    data = np.array(img_array)/255
    data = np.array(data.flatten())
    if np.avg(data) > 0.5:
        data = 1 - data
    predictedResult=model.predict(data.reshape(1, 784))[0]
    print(predictedResult)
    a = 0
    value = 0
    for i in range(len(predictedResult)):
        if predictedResult[i] > value:
            a = i
            value = predictedResult[i]
    return a

server.run(host="0.0.0.0", port=5000, debug=True)
