import '../../domain/entities/content_entity.dart';
import '../../domain/repositories/content_repository.dart';
import '../datasources/content_local_data_source.dart';

class ContentRepositoryImpl implements ContentRepository {
  final ContentLocalDataSource localDataSource;

  ContentRepositoryImpl(this.localDataSource);

  @override
  Future<List<ContentEntity>> getContentFromJson(String jsonFile) async {
    try {
      final contentModels = await localDataSource.getContentFromJson(jsonFile);
      return contentModels
          .map(
            (model) => ContentEntity(
              id: model.slideNumber.toString(),
              arabicText: model.arabicText,
              copticText: model.copticTransliteratedText,
              image: model.image,
              backgroundImage: model.backgroundImage,
              order: model.slideNumber,
            ),
          )
          .toList();
    } catch (e) {
      throw Exception('Repository error: $e');
    }
  }
}
