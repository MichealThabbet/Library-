class ContentModel {
  final int slideNumber;
  final String arabicText;
  final String? copticTransliteratedText;
  final String? image;
  final String? backgroundImage;

  ContentModel({
    required this.slideNumber,
    required this.arabicText,
    this.copticTransliteratedText,
    this.image,
    this.backgroundImage,
  });

  factory ContentModel.fromJson(Map<String, dynamic> json) {
    return ContentModel(
      slideNumber: json['slide_number'] ?? 0,
      arabicText: json['arabic_text'] ?? '',
      copticTransliteratedText: json['coptic_transliterated_text']?.isEmpty == true 
          ? null 
          : json['coptic_transliterated_text'],
      image: json['image'],
      backgroundImage: json['background_image'],
    );
  }
}
