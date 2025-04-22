import 'package:flutter/material.dart';

class RightPage extends StatelessWidget {
  final String title;

  const RightPage(this.title, {super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: Center(
        child: Text(title,style: const TextStyle(fontSize: 50),),
      ),
    );
  }
}