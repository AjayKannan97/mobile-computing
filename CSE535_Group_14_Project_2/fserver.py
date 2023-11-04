import flask
import os
import pickle
import tensorflow as tf
from tensorflow.keras.preprocessing import image
import numpy as np
from PIL import Image
from tensorflow.keras.applications.resnet50 import preprocess_input
from tensorflow import keras
import sys
import cv2
import copy
import numpy as np
from matplotlib import pyplot as plt

#server = flask.Flask(__name__)
#@server.route('/', methods=['GET', 'POST'])

#def handle_request():
#    capturedImage = flask.request.files['image']
#    predictedResult=str(getClass(capturedImage))
#    capturedImage = flask.request.files['image1']
#    savePath="./"+predictedResult+"/"
#    savePathExists = os.path.exists(savePath)
#    if not savePathExists:
#        os.makedirs(savePath)
#    capturedImage.save(savePath+capturedImage.filename)
#    return "Success"
#
def getClass(img):
    img = Image.open(img)
    img = np.asarray(img)
    img_array = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    img_array = np.expand_dims(img_array, 2)
    size=(28, 28)
    img_array=cv2.resize(img_array, size)
    #plt.imshow(img_array, interpolation='nearest')
    
    data = np.array(img_array)/255
    data = np.array(data.flatten())
    if np.average(data) > 0.5:
        data = 1 - data
    loadedModel = keras.models.load_model("finalized_model")
    predictedResult=loadedModel.predict(data.reshape(1, 784))[0]
    #print(predictedResult)
    a = 0
    value = 0
    for i in range(len(predictedResult)):
        if predictedResult[i] > value:
            a = i
            value = predictedResult[i]
    print("\n\nLabel :"+str(a))
    #plt.show()
    return

print(getClass("2.png"))
print(getClass("4.png"))
print(getClass("6.png"))
print(getClass("8.png"))
#server.run(host="0.0.0.0", port=5000, debug=True)
