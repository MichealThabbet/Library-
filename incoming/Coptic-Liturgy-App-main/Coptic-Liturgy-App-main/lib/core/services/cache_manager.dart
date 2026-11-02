import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:io';

class CacheManager {
  static final CacheManager _instance = CacheManager._internal();
  factory CacheManager() => _instance;
  CacheManager._internal();

  // Cache content for offline access
  Future<void> cacheContent(String key, Map<String, dynamic> data) async {
    final dir = await getApplicationDocumentsDirectory();
    final file = File('${dir.path}/cache_$key.json');
    await file.writeAsString(jsonEncode(data));
  }

  Future<Map<String, dynamic>?> getCachedContent(String key) async {
    try {
      final dir = await getApplicationDocumentsDirectory();
      final file = File('${dir.path}/cache_$key.json');
      if (await file.exists()) {
        final content = await file.readAsString();
        return jsonDecode(content);
      }
    } catch (e) {
      print('Cache error: $e');
    }
    return null;
  }

  // Preload critical content
  Future<void> preloadEssentialContent() async {
    // Load main menu, prayers, etc.
  }
}