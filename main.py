import PySimpleGUI as sg
import requests
import xml.etree.ElementTree as ET
from lxml.html.soupparser import fromstring

url = "http://localhost:8080/ws"
headers = {'Content-Type': 'text/xml'}
body= """<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
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

sg.change_look_and_feel('DarkAmber')	# Add a touch of color
# All the stuff inside your window.
layout = [  [sg.Text('Some text on Row 1')],
            [sg.Text('Enter something on Row 2'), sg.InputText()],
            [sg.Button('Ok'), sg.Button('Send'), sg.Button('Cancel')] ]

# Create the Window
window = sg.Window('Window Title', layout)
# Event Loop to process "events" and get the "values" of the inputs
while True:
    event, values = window.read()
    if event in (None, 'Cancel'):	# if user closes window or clicks cancel
        break
    print('You entered ', values[0])
    if event in (None, 'Send'):
        response = requests.post(url, data=body, headers=headers)
        print(response.content)
        tree = fromstring(str(response.content))
        print(tree.xpath("//activity/text()"))



window.close()