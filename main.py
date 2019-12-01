from io import BytesIO

import PySimpleGUI as sg
import requests
import xml.etree.ElementTree as ET
from lxml.html.soupparser import fromstring
from bs4 import BeautifulSoup
import xmltodict
import pandas as pd
import pytz
import datetime as dt
import matplotlib.pyplot as plt
import os

url = "http://localhost:8080/ws"
headers = {'Content-Type': 'text/xml'}
body = """<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
xmlns:med="http://www.example.com/springdemo/soap">
    <soapenv:Header/>
    <soapenv:Body>
        <med:DoctorRequest>
            <med:patientId>6</med:patientId>
            <med:sessionId/>
            <med:medicalPlanId/>
            <med:recommendation/>
        </med:DoctorRequest>
    </soapenv:Body>
</soapenv:Envelope>"""

sg.change_look_and_feel('DarkAmber')  # Add a touch of color

history = sg.Multiline('', size=(100, 20))

if os.path.isfile('activity.png'):
    os.remove('activity.png')
image = sg.Image(filename='')
layout = [[sg.Text('Some text on Row 1')],
          [sg.Text('Enter something on Row 2'), sg.InputText()],
          [sg.Button('Ok'), sg.Button('Send'), sg.Button('Cancel')],
          [history],
          [image]
          ]



# Create the Window
window = sg.Window('Window Title', layout)
# Event Loop to process "events" and get the "values" of the inputs
plot_data = {}
while True:
    event, values = window.read()
    if event in (None, 'Cancel'):  # if user closes window or clicks cancel
        break
    print('You entered ', values[0])
    if event in (None, 'Send'):
        response = requests.post(url, data=body, headers=headers)
        doc = xmltodict.parse(response.content)
        activities = doc['SOAP-ENV:Envelope']['SOAP-ENV:Body']['ns2:DoctorResponse']['ns2:activity']
        activities_to_display = ''
        for activity in activities:
            activities_to_display += activity + '\n'
            data = activity.split(',')
            for entry in data:
                aux = entry.split(':')
                if 'startTime' in aux[0]:
                    print(pd.to_datetime(aux[1], unit='ms'))
                if 'activity' in aux[0]:
                    key = aux[1]
                    key = ''.join(c for c in key if not c.isspace())
                    if key not in plot_data.keys():
                        plot_data[key] = 1
                    else:
                        plot_data[key] += 1
        print(plot_data.keys())
        plt.plot(list(plot_data.keys()), list(plot_data.values()))
        plt.tick_params(axis='x', which='major', labelsize=7)
        plt.savefig('activity.png')
        print(activities_to_display)

        history.update(activities_to_display)
        image.Update(filename='activity.png')


window.close()
