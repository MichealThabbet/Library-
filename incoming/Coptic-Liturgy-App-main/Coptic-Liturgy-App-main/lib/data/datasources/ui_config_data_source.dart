import 'dart:convert';
import 'package:flutter/services.dart';
import '../../core/utils/constants.dart';
import '../models/ui_config_model.dart';

abstract class UiConfigDataSource {
  Future<UiConfigModel> getExternalDisplayConfig();
}

class UiConfigDataSourceImpl implements UiConfigDataSource {
  @override
  Future<UiConfigModel> getExternalDisplayConfig() async {
    try {
      final String jsonString = await rootBundle.loadString('${AppConstants.assetsFilesPath}ui_config.json');
      final Map<String, dynamic> jsonData = json.decode(jsonString);
      return UiConfigModel.fromJson(jsonData['external_display']);
    } catch (e) {
      throw Exception('Failed to load UI config: $e');
    }
  }
}