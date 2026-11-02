class ContentEntity {
  final String id;
  final String arabicText;
  final String? copticText;
  final String? image;
  final String? backgroundImage;
  final int order;

  const ContentEntity({
    required this.id,
    required this.arabicText,
    this.copticText,
    this.image,
    this.backgroundImage,
    required this.order,
  });
}
