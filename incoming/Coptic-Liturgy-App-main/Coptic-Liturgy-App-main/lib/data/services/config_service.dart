import 'dart:convert';
import 'package:flutter/services.dart';
import '../models/menu_model.dart';
import '../../domain/entities/menu_entity.dart';
import '../../core/utils/constants.dart';

class ConfigService {
  static final ConfigService _instance = ConfigService._internal();
  factory ConfigService() => _instance;
  ConfigService._internal();

  static ConfigService get instance => _instance;

  Future<MenuModel> loadMenu(String fileName) async {
    try {
      final String jsonString = await rootBundle.loadString('${AppConstants.assetsFilesPath}$fileName');
      final Map<String, dynamic> jsonData = json.decode(jsonString);
      return MenuModel.fromJson(jsonData);
    } catch (e) {
      throw Exception('Failed to load menu: $e');
    }
  }

  Future<List<MenuEntity>> loadMenuItems(String fileName) async {
    try {
      final MenuModel menu = await loadMenu(fileName);
      return menu.items.map((item) => MenuEntity(
        id: item.id,
        title: item.title,
        iconName: item.icon ?? 'menu_book',
        category: item.category ?? '',
        jsonFile: item.target ?? '',
      )).toList();
    } catch (e) {
      throw Exception('Failed to load menu items: $e');
    }
  }

  Future<Map<String, dynamic>> loadContent(String fileName) async {
    try {
      final String jsonString = await rootBundle.loadString('${AppConstants.assetsFilesPath}$fileName');
      return json.decode(jsonString);
    } catch (e) {
      throw Exception('Failed to load content: $e');
    }
  }
}
