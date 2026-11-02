class MenuModel {
  final String title;
  final List<MenuItemModel> items;

  MenuModel({
    required this.title,
    required this.items,
  });

  factory MenuModel.fromJson(Map<String, dynamic> json) {
    return MenuModel(
      title: json['title'] ?? '',
      items: (json['items'] as List<dynamic>?)
          ?.map((item) => MenuItemModel.fromJson(item))
          .toList() ?? [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'title': title,
      'items': items.map((item) => item.toJson()).toList(),
    };
  }
}

class MenuItemModel {
  final String id;
  final String title;
  final String? icon;
  final String? category;
  final String? target;
  final String? description;

  MenuItemModel({
    required this.id,
    required this.title,
    this.icon,
    this.category,
    this.target,
    this.description,
  });

  factory MenuItemModel.fromJson(Map<String, dynamic> json) {
    return MenuItemModel(
      id: json['id'] ?? '',
      title: json['title'] ?? '',
      icon: json['icon'],
      category: json['category'],
      target: json['target'],
      description: json['description'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'icon': icon,
      'category': category,
      'target': target,
      'description': description,
    };
  }
}
