// ignore_for_file: prefer_const_constructors

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class PhoneCall extends StatelessWidget {
  final Function callNumber;
  final myController = TextEditingController();

  PhoneCall(this.callNumber);

  @override
  Widget build(BuildContext context) {
    return Center(
        child: Card(
      color: Color.fromARGB(255, 66, 66, 66),
      child: Container(
        margin: EdgeInsets.all(20.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              "Phone Call",
              style: TextStyle(
                  fontSize: 28, color: Color.fromARGB(255, 145, 255, 240)),
            ),
            SizedBox(
              height: 20,
            ),
            TextField(
              decoration: InputDecoration(
                focusedBorder: OutlineInputBorder(
                    borderSide:
                        BorderSide(color: Color.fromARGB(255, 145, 255, 240))),
                border: OutlineInputBorder(),
                labelText: 'Enter Phone Number',
                labelStyle:
                    TextStyle(color: Color.fromARGB(255, 145, 255, 240)),
                constraints: BoxConstraints.tightFor(width: 300),
              ),
              style: TextStyle(color: Colors.white),
              controller: myController,
              keyboardType: TextInputType.number,
              inputFormatters: [FilteringTextInputFormatter.digitsOnly],
            ),
            SizedBox(
              height: 20,
            ),
            ElevatedButton(
                style: ElevatedButton.styleFrom(
                    primary: Color.fromARGB(255, 145, 255, 240),
                    onPrimary: Colors.black),
                onPressed: (() async {
                  await callNumber(myController.text);
                }),
                child: Text('Call')),
          ],
          mainAxisAlignment: MainAxisAlignment.center,
        ),
      ),
      margin: EdgeInsets.all(20),
    ));
  }
}
