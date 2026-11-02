import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AppStateManager extends ChangeNotifier {
  bool _isDarkMode = false;
  double _fontSize = 16.0;
  String _currentLanguage = 'ar';
  String _selectedFont = 'Default';

  bool get isDarkMode => _isDarkMode;
  double get fontSize => _fontSize;
  String get currentLanguage => _currentLanguage;
  String get selectedFont => _selectedFont;

  AppStateManager() {
    _loadSettings();
  }

  void toggleDarkMode() {
    _isDarkMode = !_isDarkMode;
    _saveSettings();
    notifyListeners();
  }

  void increaseFontSize() {
    if (_fontSize < 24.0) {
      _fontSize += 2.0;
      _saveSettings();
      notifyListeners();
    }
  }

  void decreaseFontSize() {
    if (_fontSize > 12.0) {
      _fontSize -= 2.0;
      _saveSettings();
      notifyListeners();
    }
  }

  void setLanguage(String language) {
    _currentLanguage = language;
    _saveSettings();
    notifyListeners();
  }

  void setFont(String font) {
    _selectedFont = font;
    _saveSettings();
    notifyListeners();
  }

  Future<void> _loadSettings() async {
    final prefs = await SharedPreferences.getInstance();
    _isDarkMode = prefs.getBool('isDarkMode') ?? false;
    _fontSize = prefs.getDouble('fontSize') ?? 16.0;
    _currentLanguage = prefs.getString('currentLanguage') ?? 'ar';
    _selectedFont = prefs.getString('selectedFont') ?? 'Default';
    notifyListeners();
  }

  Future<void> _saveSettings() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setBool('isDarkMode', _isDarkMode);
    await prefs.setDouble('fontSize', _fontSize);
    await prefs.setString('currentLanguage', _currentLanguage);
    await prefs.setString('selectedFont', _selectedFont);
  }
}


