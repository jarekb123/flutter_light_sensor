import 'package:flutter/material.dart';
import 'package:light_sensor/light_sensor.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  LightSensor lightSensor = LightSensor();
  dynamic _lux = 0.0;

  @override
  void initState() {
    super.initState();
    lightSensor.startListening(_onLightSensorEvent);
  }

  void _onLightSensorEvent(dynamic lux) {
    setState(() {
      _lux = lux;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Light: $_lux'),
        ),
      ),
    );
  }
}
