import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../core/utils/constants.dart';
import '../../domain/entities/content_entity.dart';
import '../../domain/usecases/get_content_usecase.dart';
import '../../data/repositories/content_repository_impl.dart';
import '../../data/datasources/content_local_data_source.dart';
import '../widgets/content/content_list_view.dart';
import 'presentation_page.dart';

class ReadingsPage extends StatefulWidget {
  const ReadingsPage({super.key});

  @override
  State<ReadingsPage> createState() => _ReadingsPageState();
}

class _ReadingsPageState extends State<ReadingsPage> {
  late Future<List<ContentEntity>> _contentFuture;
  late GetContentUseCase _getContentUseCase;

  @override
  void initState() {
    super.initState();
    final contentDataSource = ContentLocalDataSourceImpl();
    final contentRepository = ContentRepositoryImpl(contentDataSource);
    _getContentUseCase = GetContentUseCase(contentRepository);
    _contentFuture = _getContentUseCase.execute(AppConstants.readingsFile);
  }

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('القراءات'),
        actions: [
          IconButton(
            onPressed: () => _startPresentation(context),
            icon: const Icon(Icons.slideshow),
            tooltip: 'بدء العرض',
          ),
        ],
      ),
      body: FutureBuilder<List<ContentEntity>>(
        future: _contentFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('حدث خطأ: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('لا يوجد محتوى'));
          }

          final content = snapshot.data!;
          return ContentListView(
            contentList: content,
            currentFile: AppConstants.readingsFile,
            onSlideSelected:
                (index) => _startPresentation(context, initialSlide: index),
          );
        },
      ),
    );
  }

  void _startPresentation(BuildContext context, {int initialSlide = 0}) async {
    final content = await _contentFuture;
    if (content.isNotEmpty) {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder:
              (context) => PresentationPage(
                content: content,
                title: 'القراءات',
                initialSlide: initialSlide,
              ),
        ),
      );
    }
  }
}
