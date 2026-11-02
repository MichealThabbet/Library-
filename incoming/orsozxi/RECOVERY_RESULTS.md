# نتائج الحفاظ على الأكواد وفك التشفير

تمت إضافة النتائج فقط داخل هذا المجلد، بدون حذف أو تعديل أي ملف من الملفات الأصلية البالغ عددها 11,385 ملفًا (ومنها ملف readme الموجود مسبقًا). تمت مطابقة كل أصل مع نسخة Git السابقة بالبايت. الأجزاء الـ11 سبق حذفها في الخطوة السابقة، ولم تتم إعادة كتابة سجل Git في هذه الخطوة.

## المحتوى المفكوك

| النوع | النتيجة |
|---|---|
| ملفات ORSO المشفرة | 955 ملفًا، جميعها مفكوكة في `DECRYPTED_CONTENT/documents` مع الاحتفاظ بمساراتها |
| ملفات Excel | 1,329 مصنفًا، 1,335 ورقة، مصدرة إلى CSV في `DECRYPTED_CONTENT/spreadsheets` |
| خلايا Excel المشفرة | 300,494 خلية مفكوكة، مع الحفاظ على بقية الخلايا؛ إجمالي 812,187 موضع خلية |
| قاعدة الكتاب المقدس | قاعدة SQLite مفتوحة وCSV في `DECRYPTED_CONTENT/bible`، تضم 35,784 سجلًا |
| مخازن BKS | 32 شهادة عامة مصدرة من 3 مخازن إلى PEM؛ لا توجد مفاتيح مشفرة داخلها. الشهادات مخزنة أصلًا دون تشفير، ولم يتم التحقق من MAC الخاص بالمخازن |

تمت إعادة تشفير كل ملف ORSO وكل خلية مفكوكة ومطابقتها بالمصدر المشفر. فحص SQLite أعاد `ok`، وتمت مقارنة جميع السجلات المفكوكة بالمصدر وتطابقت بالكامل. لا توجد أخطاء فك تشفير في الأنواع المحددة أعلاه. ملفات النصوص الأخرى فُحصت لنمط Base64/hex المشفر المستخدم في التطبيق ولم تظهر مطابقات إضافية. ملفات Office المساعدة والخطوط والصور والموارد الثنائية ليست جميعها ملفات مشفرة.

التقرير التفصيلي ومسارات المخرجات: [verification.json](DECRYPTED_CONTENT/verification.json).

## الأكواد والحساب الكنسي

- ملفات `classes.dex` و`classes2.dex` و`classes3.dex` الأصلية محفوظة كما هي، وكذلك 55 مكتبة أصلية `.so` وجميع الموارد.
- تمت إضافة 12,308 ملفات Java مسترجعة والموارد المفكوكة في [RECOVERED_CODE](RECOVERED_CODE/).
- تم تفريغ تعليمات ملفات DEX الثلاثة: 23,236 صنفًا، و149,800 دالة ذات تعليمات، و2,179,967 تعليمة، في [dex_instructions](RECOVERED_CODE/dex_instructions/). التفريغ مضغوط بصيغة gzip ويتضمن إزاحات التعليمات وبايتاتها.
- ملفات Java المسترجعة ليست المشروع الأصلي القابل للبناء. توجد 799 دالة لم يستكمل JADX تحويلها في وضعه المعتاد، منها 23 ضمن حزمة التطبيق. التفاصيل في [decompilation_limits.json](RECOVERED_CODE/decompilation_limits.json). الأصول وتعليمات DEX محفوظة لهذه الدوال أيضًا.

تم التأكد من وجود حساب عيد القيامة ودورات 19 و4 و7 سنوات، وربط المناسبات والقراءات بالتقويم القبطي في:

1. [Katamaras.java](RECOVERED_CODE/sources/com/app/orsozoxi/Katamaras.java): `getOrthodoxEaster` و`calculateEvents`.
2. [katamarsReaders.java](RECOVERED_CODE/sources/com/app/orsozoxi/katamarsReaders.java): تحميل قراءات الأيام والآحاد وصوم يونان والصوم الكبير والخماسين.
3. [نسخة التعليمات المبسطة للقارئ](RECOVERED_CODE/calendar_simple/sources/com/app/orsozoxi/katamarsReaders.java): عرض إضافي للدوال التي لم تُسترجع جيدًا في الوضع المعتاد؛ يحتوي على تعليمات وقفزات، وليس Java مضمون البناء.
4. [CopticCalendar.java](RECOVERED_CODE/sources/com/ibm/icu/util/CopticCalendar.java) و[CECalendar.java](RECOVERED_CODE/sources/com/ibm/icu/util/CECalendar.java): تنفيذ التقويم القبطي المستخدم من مكتبة ICU.
5. [PsalmodySanawyReaders.java](RECOVERED_CODE/sources/com/app/orsozoxi/PsalmodySanawyReaders.java) و[SanksarReaders.java](RECOVERED_CODE/sources/com/app/orsozoxi/SanksarReaders.java): حسابات مرتبطة بالإبصلمودية والسنكسار.

هذا توثيق للمنطق الموجود في التطبيق؛ لم تُجرَ مقارنة شاملة لصحة كل قراءة ومناسبة مع مرجع كنسي مستقل. معادلة القيامة الأصلية تستخدم فرق 13 يومًا ثابتًا؛ تم الحفاظ عليها دون تعديل.

## إعادة التنفيذ والتحقق

أدوات الاستخراج في [RECOVERY_TOOLS](RECOVERY_TOOLS/)، وتستخرج معاملات فك التشفير من موارد التطبيق نفسه. استُخدم JADX 1.5.3 لاسترجاع Java. ملف [original_files_sha256.json](RECOVERED_CODE/original_files_sha256.json) يوثق بصمات كل الملفات الأصلية. هذه النتائج ناتجة عن فحص الملفات المرفقة في هذا الإصدار؛ لا تشمل محتوى يُحمّل من خوادم خارجية أثناء تشغيل التطبيق.
