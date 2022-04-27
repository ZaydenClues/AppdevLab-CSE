import 'package:flutter/material.dart';
import 'package:phone_call/homeScreen.dart';
import 'package:splashscreen/splashscreen.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyAppState();
  }
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: SplashScreen(
        seconds: 2,
        navigateAfterSeconds: HomeScreen(),
        title: Text(
          "Phone App",
          style: TextStyle(fontSize: 32, color: Colors.white),
        ),
        loadingText: Text(
          "Loading...",
          style: TextStyle(color: Colors.white),
        ),
        backgroundColor: Color.fromARGB(255, 43, 86, 92),
      ),
    );
  }
}
