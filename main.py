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

patient_id = 10
url = "http://localhost:8080/ws"
headers = {'Content-Type': 'text/xml'}
history_body = '''<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
xmlns:med="http://www.example.com/springdemo/soap">
    <soapenv:Header/>
    <soapenv:Body>
        <med:DoctorRequest>
            <med:patientId>''' + f'{patient_id}' + '''</med:patientId>
            <med:sessionId/>
            <med:medicalPlanId/>
            <med:recommendation/>
        </med:DoctorRequest>
    </soapenv:Body>
</soapenv:Envelope>'''

medical_plan_id = ''
recommendation = ''

if os.path.isfile('activity.png'):
    os.remove('activity.png')

sg.change_look_and_feel('DarkAmber')  # Add a touch of color

history = sg.Multiline('', size=(100, 20))
image = sg.Image(filename='')

medical_plan = sg.Text('', size=(100, 1))


recommendation_input = sg.InputText()
activity_input = sg.InputText()

layout = [[sg.Text('PatientId'), sg.InputText(), sg.Text('Recommendation'), recommendation_input],
          [sg.Text('MedicalPlanId'), sg.InputText(), sg.Text('Activity'), activity_input],
          [sg.Button('Check plan'), sg.Button('Send'), sg.Button('Add recommendation')],
          [medical_plan],
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

    if event in (None, 'Check plan'):
        patient_id = values[0]
        medical_plan_id = values[2]
        medical_plan_body = '''<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
        xmlns:med="http://www.example.com/springdemo/soap">
            <soapenv:Header/>
            <soapenv:Body>
                <med:DoctorRequest>
                    <med:patientId>''' + f'{patient_id}' + '''</med:patientId>
                    <med:sessionId/>
                    <med:medicalPlanId>''' + f'{medical_plan_id}' + '''</med:medicalPlanId>
                    <med:recommendation/>
                </med:DoctorRequest>
            </soapenv:Body>
        </soapenv:Envelope>'''
        response = requests.post(url, data=medical_plan_body, headers=headers)
        doc = xmltodict.parse(response.content)
        medical_plan.update(doc['SOAP-ENV:Envelope']['SOAP-ENV:Body']['ns2:DoctorResponse']['ns2:medicalPlan'])

        # parse the resulted xml

    if event in (None, 'Add recommendation'):
        recommendation = values[1]
        activity_id = values[3]
        recommendation_body = '''<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
        xmlns:med="http://www.example.com/springdemo/soap">
            <soapenv:Header/>
            <soapenv:Body>
                <med:DoctorRequest>
                    <med:patientId>''' + f'{patient_id}' + '''</med:patientId>
                    <med:sessionId>''' + f'{activity_id}' + '''</med:sessionId>
                    <med:medicalPlanId/>
                    <med:recommendation>''' + f'{recommendation}' + '''</med:recommendation>
                </med:DoctorRequest>
            </soapenv:Body>
        </soapenv:Envelope>'''
        requests.post(url, data=recommendation_body, headers=headers)
        recommendation_input.update('')
        activity_input.update('')


    if event in (None, 'Send'):
        response = requests.post(url, data=history_body, headers=headers)
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
