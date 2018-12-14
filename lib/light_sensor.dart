import 'dart:async';

import 'package:flutter/services.dart';

class LightSensor {
  static const _stream = const EventChannel("com.butajlo.lightsensor/stream");

  StreamSubscription _sensorSubscription;

  void startListening(Function(dynamic) onEvent) {
    if(_sensorSubscription == null) {
      _sensorSubscription = _stream.receiveBroadcastStream().listen(onEvent);
    }
  }

  void stopListening() {
    if(_sensorSubscription != null) {
      _sensorSubscription.cancel();
      _sensorSubscription = null;
    }
  }

}
