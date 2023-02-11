# Raspberry Pi based smart watch
 Raspberry Pi Zero W based child location and health tracking smartwatch built using Android and Google Firebase.

In the present world, safety of children is one of the major concerns of parents. Many parents, specially working parents; do not have enough time to keep eye on their child and their well-being. Our product Kids Monitoring Band is a product designed for the safety and welfare of the child. The monitoring band is equipped with many features to ensure the security and well-being with instant notification to the parents. The product is equipped with heart rate sensor which constantly detects the heart rate and a temperature sensor which detects the body temperature of the child. If either the heart rate or the body temperature increases a certain threshold, the parents are instantly alerted via a notification on their android phones. Moreover, parents are also alerted if the band is taken off by the child.

Furthermore, the band is also equipped with GPS which helps the parent know the whereabouts of the child. They can set a perimeter within which the child can feel safe and if the child leaves the perimeter, an instant notification is sent to the android application. The parents can always access the above information via their android applications and communicate with their children via a buzzer and an LCD screen which displays the in-built messages on the android app. The success of our project can be measured by the achieved goals set by the team in the initial stages of the project [2]. All the above mentioned features were implemented successfully in the final stage.

Functional Specifications:

The main functions of the monitoring band are the GPS, heart rate and temperature sensing and a buzzer along with LCD for communication with instant notification feature. All the above information is displayed on the android application. Detailed specifications of each function is listed below.

GPS based tracking:
- Accuracy on parents Android OS device to be around +/- 10 meteres
- 2 parameter (radius and centre point) safe zone( acceptable area)
- Location shown on Android device
- Notification on Android
- 30 samples/hour

Body Temperature reading:
- Body temperature reading to within +/- 1 degrees Celsius
- Configurable nominal body temperature range
- Parents to be notified on phone if body temperature out of nominal range
- 30 samples/hour

Pulse rate reading:
- Pulse rate to be updated every 2 minutes
- Configurable nominal body temperature range
- Parents to be notified on phone if body temperature out of nominal range
- 30 samples/hour

Buzzer:
- High response time (<2s)
- Different buzzing rhythm/sequence corresponding to differnt instructions from parents
- Buzzer cannot be turned off by child
- Buzzer to beep when abnormal activity detected(Out of GPS range)

LCD:
- Message display time to be less than 2 secs.
- Variable messages for children of different ages
- Bitmap picture based messges( for easy understanding on childs part)



![image](https://user-images.githubusercontent.com/37940040/218284496-4ff8be5a-a52d-42f7-ae5e-a2ff418dd289.png)

![image](https://user-images.githubusercontent.com/37940040/218284505-e2228e79-a672-4503-b1ba-c113959f4f63.png)
