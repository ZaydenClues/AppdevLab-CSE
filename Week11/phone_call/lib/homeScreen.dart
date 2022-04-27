import 'package:flutter/material.dart';
import 'package:flutter_phone_direct_caller/flutter_phone_direct_caller.dart';
import 'package:splashscreen/splashscreen.dart';
import './phoneCall.dart';

class HomeScreen extends StatefulWidget {
  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  void _callNumber(String phoneNumber) async {
    print('Phone Number: $phoneNumber');
    await FlutterPhoneDirectCaller.callNumber(phoneNumber);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Phone Call App'),
        centerTitle: true,
        backgroundColor: Color.fromARGB(255, 161, 244, 255),
        titleTextStyle:
            TextStyle(fontSize: 22, color: Color.fromARGB(255, 0, 0, 0)),
      ),
      body: Container(
          decoration: BoxDecoration(
              image: DecorationImage(
            image: AssetImage("assets/bg.png"),
            fit: BoxFit.cover,
          )),
          child: PhoneCall(_callNumber)),
    );
  }
}
