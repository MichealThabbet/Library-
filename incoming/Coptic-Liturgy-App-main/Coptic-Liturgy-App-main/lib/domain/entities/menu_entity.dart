class MenuEntity {
  final String id;
  final String title;
  final String iconName;
  final String category;
  final String? jsonFile;
  final String? description;

  MenuEntity({
    required this.id,
    required this.title,
    required this.iconName,
    required this.category,
    this.jsonFile,
    this.description,
  });
}