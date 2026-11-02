package com.app.piapostolos;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.Bible;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.Views.MenuDateView;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.navdrawer.SimpleSideDrawer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class MainActivity_mdaya7 extends BaseActivity {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterglav;
    String[] agynames;
    int chaptersnumbers;
    private View content;
    int counterr;
    private Context ctx;
    Dictionary<Integer, String> d;
    private GestureDetector gestureDetector;
    private GestureDetector gestureDetector2;
    AdapterView.AdapterContextMenuInfo info;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private ArrayList<String> listSpinner1;
    private MenuDateView menuDateView;
    WebView myWebView;
    ArrayList<String> sp2;
    Spinner spinner;
    Spinner spinner2;
    TextView t;
    WebView we;
    int def = 0;
    int pos = 0;
    int selectedChild = 0;
    String[] selectedSubChild = null;
    String[][] childs1 = {new String[]{"في كنيسة الغالبين", "السلام لقديس عظيم"}, new String[]{"أنا أفتح فاى بخشوع"}, new String[]{"أبدي باسم الآب خالقنا", "فرحًا نفرح بالرب"}, new String[]{"السلام للقديسين"}, new String[]{"السلام لقديس عظيم"}, new String[]{"بالتمجيد والتسبيح"}, new String[]{"في مجد السمائيين", "السلام للقديسين", "كان رب المجد مع البار"}, new String[]{"مع الكهنة الابرار"}, new String[]{"في مجمع الأبرار", "السلام لك يا آبا فام"}, new String[]{"فى مدينة قصر هور"}, new String[]{"أبدأ باسم إله النور"}, new String[]{"فى حلة ملائكية"}, new String[]{"في برية شيهات", "أبدى باسم اله قدير", "طوباك يا أبو مقار", "نصيح بتراتيل وألحان"}, new String[]{"ذو الأصل الطاهر", "ترك الأرضيات", "أفتح فاي بالأفراح"}, new String[]{"أثناسيوس يا رسول", "السلام لحامي الإيمان", "السلام لك يا بطل", "أثناسيوس يا قديس"}, new String[]{"أبونا أثناسيوس السريانى"}, new String[]{"أبدأ باسم إلهنا", "السلام لشهداء أخميم"}, new String[]{"السلام للعذارى"}, new String[]{"أبدأ بكل ثبات"}, new String[]{"فى بستان الرهبان"}, new String[]{"أقدم التمجد", "السلام لك يا استفانوس", "أنا أفتح فمي بحمد الله"}, new String[]{"لنتهلل بتقديم"}, new String[]{"باسم الثالوث الوحيد"}, new String[]{"أبدأ باسم الله القدوس"}, new String[]{"أبدي وأقول أكسيوس", "في كنيسة المنتصرين"}, new String[]{"السلام لك يا مختارة"}, new String[]{"السلام لفخر العذارى"}, new String[]{"نسبح رب القوات"}, new String[]{"تعالوا يا مؤمنين"}, new String[]{"تعالوا يا احباء نطلب اليوم نداء"}, new String[]{"أبدى باسم الله ربى"}, new String[]{"فى كنيسة المنتصرين"}, new String[]{"في كنيسة الابكار"}, new String[]{"في كنيسة الأبكار", "أبدأ بالتمجيد والوقار"}, new String[]{"لأمجد الإله"}, new String[]{"السلام لك يا تاماف", "السلام للمختارة"}, new String[]{"سبحوا الله في قديسه"}, new String[]{"أبدأ يا مؤمنين"}, new String[]{"في صفوف العذارى", "أبدأ باسم الله الحنان"}};
    String[][] childsFiles1 = {new String[]{"a1", "a2"}, new String[]{"a3"}, new String[]{"a4", "a5"}, new String[]{"a6"}, new String[]{"a7"}, new String[]{"a8"}, new String[]{"a10", "a11", "a12"}, new String[]{"a13"}, new String[]{"a14", "a15"}, new String[]{"a16"}, new String[]{"a17"}, new String[]{"a18"}, new String[]{"a19", "a20", "a21", "a22"}, new String[]{"a23", "a24", "a25"}, new String[]{"a26", "a27", "a28", "a29"}, new String[]{"a30"}, new String[]{"a31", "a32"}, new String[]{"a33"}, new String[]{"a34"}, new String[]{"a35"}, new String[]{"a36", "a37", "a38"}, new String[]{"a39"}, new String[]{"a40"}, new String[]{"a41"}, new String[]{"a42", "a43"}, new String[]{"a44"}, new String[]{"a45"}, new String[]{"a46"}, new String[]{"a47"}, new String[]{"a48"}, new String[]{"a49"}, new String[]{"a50"}, new String[]{"a51"}, new String[]{"a52", "a53"}, new String[]{"a54"}, new String[]{"a55", "a56"}, new String[]{"a57"}, new String[]{"a58"}, new String[]{"a59", "a60"}};
    String[] groups1 = {"الشهيد أباسخيرون القليني", "الشهيد أباكراجون البتانوني", "الشهيدان أباكير ويوحنا القديسان", "الشهداء يسطس وأبالى وثاؤكليا", "الشهيد أبانوب النهيسى", "القديسة أبراكسيا", "القديس الأنبا إبرآم أسقف الفيوم", "القديس ابونا ابراهيم البسيط", "الشهيد ابو فام الجندى", "القديس ابو فانا", "الشهيد أبو قسطور البردنوهي", "القديسان الأنبا أبوللو والأنبا أبيب", "القديس أبو مقار الكبير", "القديس ابو نوفر السائح", "القديس البابا أثناسيوس", "الراهب القمص أثناسيوس السرياني", "شهداء أخميم", "الشهيدتان أدروسيس و يوأنة", "الأربعة حيوانات غير المتجسدين", "القديس الأنبا أرسانيوس", "الشهيد استفانوس", "الشهيد اسحق الدفراوي", "الشهيدة أغاثي", "القديس إغريغوريوس الراهب", "القديس أغسطينوس", "القديسة إفروسينا", "الشهيدة إفرونيا العفيفة", "الشهيد إقلاديوس الأمير", "القديس أمبروسيوس أسقف ميلان", "القديس الأنبا امون السائح", "الشهيد الأنبا أمونيوس", "الشهيد أندراوس الرسول", "القديس أبونا اندراوس الصموئيلي", "القديس الأنبا أنطونيوس", "الشهيدة إيريني", "الأم إيرينى (تماف ايريني)", "القديس الأنبا إيسيذورس", "القديس إيلاريون تلميذ الأنبا أنطونيوس", "القديسة إيلارية"};
    String[][] childs2 = {new String[]{"أبدأ باسم القدوس"}, new String[]{"السلام لك يا قديس", "أفتح فاي بالأفراح", "السلام لك يا أنبا باخوميوس"}, new String[]{"لك التمجيد مع القدرة"}, new String[]{"امجد الهى بلسان فصيح"}, new String[]{"نُمجد إلهنا"}, new String[]{"فى مواكب القديسات"}, new String[]{"الرب أحبنا"}, new String[]{"ابدا بنشيد ومديح"}, new String[]{"في بداية كل مديح أقول بأسم المسيح"}, new String[]{"السلام للبطل الشهيد"}, new String[]{"في يوم تذكار دخول"}, new String[]{"تقدمت انا المسكين"}, new String[]{"أفتح فاي بالتسبيح", "أفتح فاي بالتمجيد"}, new String[]{"أبدأ بإسم الإله"}, new String[]{"أنا أفتح فمي وأتكلم", "في صعيد بلادي"}, new String[]{"في حلة نورانية", "يا أنبا بولا"}, new String[]{"في سماء ربي الحنان"}, new String[]{"في كنيسة الغالبين", "السلام لك يا قديس"}, new String[]{"بحكمة الهيه"}, new String[]{"فى سيرة روحانية"}};
    String[][] childsFiles2 = {new String[]{"b1"}, new String[]{"b2", "b3", "b4"}, new String[]{"b5"}, new String[]{"b6"}, new String[]{"b7"}, new String[]{"b8"}, new String[]{"b9"}, new String[]{"b10"}, new String[]{"b11"}, new String[]{"b12"}, new String[]{"b13"}, new String[]{"b14"}, new String[]{"b15", "b16"}, new String[]{"b17"}, new String[]{"b18", "b19"}, new String[]{"b20", "b21"}, new String[]{"b22"}, new String[]{"b23", "b24"}, new String[]{"b25"}, new String[]{"b26"}};
    String[] groups2 = {"القديسة بائيسة", "القديس الأنبا باخوميوس أب الشركة", "الشهيد يوليان والقديسة باسيليس", "الشهيدة بربارة", "الشهيدة بربارة والشهيدة يوليانة", "الشهيدة بربتوا", "الشهيد برثلماوس الرسول", "القديس الأنبا برسوم العريان", "القديس الأنبا بسادة", "الشهيد بستافروس الجديد", "الشهيد بشنونة المقاري", "الشهيد الأنبا بضابا", "الشهيد البابا بطرس خاتم الشهداء", "الشهيد بطرس الرسول", "القديس الأنبا بولا الطموهي", "القديس الأنبا بولا أول السواح", "الشهيد بولس الرسول", "القديس الأنبا بيشوي حامل المسيح", "القديس ابونا بيشوي كامل", "القديس الأنبا بيمن المتوحد"};
    String[][] childs3 = {new String[]{"أنا أفتح فاي وأصيح", "أبدأ باسم الديان"}, new String[]{"أبدي باسم الله الغفار", "المجد لله القدوس"}, new String[]{"السلام لتداوس الحكيم"}, new String[]{"السلام لك والاكرام", "نبدأ يا مؤمنين", "نسجد لاسم القدوس"}, new String[]{"أفتح فاى بالتسبيح"}};
    String[][] childsFiles3 = {new String[]{"c1", "c2"}, new String[]{"c3", "c4"}, new String[]{"c5"}, new String[]{"c6", "c7", "c9"}, new String[]{"c10"}};
    String[] groups3 = {"الشهيد تادرس الشطبى الأمير", "الشهيد تادرس المشرقى", "الشهيد القديس تداوس الرسول", "القديس الأنبا تكلا هيمانوت الحبشي القس", "القديس الأنبا توماس السائح"};
    String[][] childs4 = {new String[]{"السلام للقديسين"}, new String[]{"شهيَة سيرة القديسين"}};
    String[][] childsFiles4 = {new String[]{"d1"}, new String[]{"d2"}};
    String[] groups4 = {"الشهداء يسطس وأبالى وثاؤكليا", "الشهيدة ثيؤدورا العفيفة"};
    String[][] childs6 = {new String[]{"في كورة الأحياء"}, new String[]{"من عيلة نقية"}};
    String[][] childsFiles6 = {new String[]{"e1"}, new String[]{"e2"}};
    String[] groups6 = {"القديسان يواقيم وحنة", "القديسة حنة والدة العذراء مريم"};
    String[][] childs8 = {new String[]{"الأخوة القديسين", "السلام للمختارين", "السلام للقديسين"}, new String[]{"أنا أرتل بالأنغام", "عظيمة بالحقيقة", "بدر ساطع بأنوار بهية", "أشرقت شمس الغفران"}, new String[]{"اسبح الرب من اعماقى"}, new String[]{"أبدأ باسم الله القدوس سيدنا ايسوس بى اخرستوس"}, new String[]{"بطل الارثوذكسية"}, new String[]{"أبدأ بِاسم القدوس"}};
    String[][] childsFiles8 = {new String[]{"f1", "f2", "f3"}, new String[]{"f4", "f5", "f6", "f7"}, new String[]{"f8"}, new String[]{"f9"}, new String[]{"f10"}, new String[]{"f11"}};
    String[] groups8 = {"الشهيدان قزمان ودميان", "الشهيدة دميانة", "الشهيدة دولاجى و أبنائها الأربعة", "القديسان مكسيموس و دوماديوس", "القديس البابا ديسقوروس", "القديس البابا ديمتريوس الكرام الـ12"};
    String[][] childs10 = {new String[]{"السلام لك يا رفائيل"}, new String[]{"سنباط غنية"}, new String[]{"كان عايش فى غربه", "أفتح فاي بإسم الله", "السلام للأنبا رويس", "السلام للأنبا فريج"}};
    String[][] childsFiles10 = {new String[]{"g1"}, new String[]{"g2"}, new String[]{"g3", "g4", "g5", "g6"}};
    String[] groups10 = {"الملاك رافائيل رئيس الملائكة", "الشهيدة رفقة و أولادها الخمسة", "القديس الأنبا رويس"};
    String[][] childs11 = {new String[]{"السلام لك يا زخارياس الأسقف والقديس"}};
    String[][] childsFiles11 = {new String[]{"h1"}};
    String[] groups11 = {"القديس الانبا زخارياس"};
    String[][] childs12 = {new String[]{"فى سماء المنتصرين"}, new String[]{"الرب قد أعطانى"}, new String[]{"أبدأ باسم الله القدوس"}, new String[]{"أبدأ باسم الله الديان"}, new String[]{"فى كنيسة الابرار"}, new String[]{"السلام لك يا سمعان يا قديس المُقطم"}, new String[]{"رابع سبعة رؤساء"}, new String[]{"توكلت عليك يا الهي"}, new String[]{"فى كنيسة المنتصرين"}};
    String[][] childsFiles12 = {new String[]{"i1"}, new String[]{"i2"}, new String[]{"i3"}, new String[]{"i5"}, new String[]{"i6"}, new String[]{"i7"}, new String[]{"i8"}, new String[]{"i9"}, new String[]{"i10"}};
    String[] groups12 = {"الأم سارة رئيسة دير الأنبا بضابا", "القديس الأنبا ساويرس البطريرك الأنطاكي", "السبعون رسول", "الشهيد سرابيون", "الشهيدان سرجيوس وواخوس", "القديس سمعان الأخميمى", "القديس سمعان الخراز", "الملاك سوريال رئيس الملائكة الجليل", "الشهيد سيدهم بشاي", "الشهيد سيرابيون"};
    String[][] childs13 = {new String[]{"المجد والتقديس", "أبدأ باسم الله القدير"}, new String[]{"من أفاضل الأباء", "فى محفل الأبرار"}};
    String[][] childsFiles13 = {new String[]{"j1", "j2"}, new String[]{"j3", "j4"}};
    String[] groups13 = {"القديس الأنبا شنودة رئيس المتوحدين", "القديس الأنبا شنودة الثالث"};
    String[][] childs14 = {new String[]{"مع كواكب الأبرار على أعلى منار"}, new String[]{"أنا أبدأ بالتمجيد وأوصف بكل شجون"}, new String[]{"بنعمة وحب يقين"}, new String[]{"السلام للبطل الشهيد"}, new String[]{"في صفوف المعترفين"}};
    String[][] childsFiles14 = {new String[]{"k1"}, new String[]{"k2"}, new String[]{"k3"}, new String[]{"k4"}, new String[]{"k5"}};
    String[] groups14 = {"القديس الأنبا صرابامون أبو طرحة", "الشهيد الأنبا صرابامون أسقف نيقوس", "الأب الراهب أبونا صليب آفامينا", "الشهيد صليب الجديد", "الأنبا صموئيل المعترف"};
    String[][] childs18 = {new String[]{"انا افتح فمى بالتسبيح", "فى كنيسة المنتصرين", "نياحتك خدتنا يا شيخ كهنتنا"}};
    String[][] childsFiles18 = {new String[]{"l1", "l2", "l3"}};
    String[] groups18 = {"القديس أبونا عبد المسيح المنهري"};
    String[][] childs19 = {new String[]{"السلام لك يا غبرييل", "عظيمة كرامتك", "السلام لك يا غبريال"}, new String[]{"ابدأ باسم الله ربي"}};
    String[][] childsFiles19 = {new String[]{"m1", "m2", "m3"}, new String[]{"m4"}};
    String[] groups19 = {"الملاك غبريال رئيس الملائكة", "القديس غريغوريوس العجائبي"};
    String[][] childs20 = {new String[]{"نُمجد الإله"}, new String[]{"نرتل بلسان فصيح"}, new String[]{"السلام للقديسة"}, new String[]{"أفتح فاي بالتسبيح", "أنا افتح فاى بالتسبيح", "تعالوا نتهلل بالألحان", "أمدح فيكي يا ماريام"}, new String[]{"نسبح الإله"}, new String[]{"ابدا باسم الهنا"}};
    String[][] childsFiles20 = {new String[]{"n1"}, new String[]{"n2"}, new String[]{"n3"}, new String[]{"n4", "n5", "n6", "n7"}, new String[]{"n8"}, new String[]{"n9"}};
    String[] groups20 = {"الشهيدة فبرونيا الراهبة", "القديسة فيرونيكا", "الشهيدة فيرينا", "الشهيد فيلوباتير مرقوريوس أبو سيفين", "الشهيد فيلوثاؤس", "شهداء الفيوم"};
    String[][] childs21 = {new String[]{"الأخوة القديسين", "السلام للمختارين", "السلام للقديسين"}, new String[]{"طوباك يا انبا قلتا ياحبيب ابن اللة"}};
    String[][] childsFiles21 = {new String[]{"o1", "o2", "o3"}, new String[]{"o4"}};
    String[] groups21 = {"الشهيدان قزمان و دميان", "الشهيد الأنبا قلتة الطبيب"};
    String[][] childs22 = {new String[]{"في كنيسة الأبكار"}, new String[]{"سمع الأنبا بموا", "في سماء القديسين1", "في سماء القديسين2", "بمحبة نارية"}, new String[]{"في كواكب الفردوس", "على طريق الجلجثة", "السلام لك يا بابا كيرلس", "في كنيسة القديسين", "يا رجل الصلوات"}, new String[]{"أفتح فاح بالتسبيح", "السلام لعمود الدين"}, new String[]{"أبدأ باسم الإله القدوس", "اسمك جميل معناه رباني", "في حضن القديسين"}};
    String[][] childsFiles22 = {new String[]{"p1"}, new String[]{"p2", "p3", "p4", "p5"}, new String[]{"p6", "p7", "p8", "p9", "p10"}, new String[]{"p11", "p12"}, new String[]{"p13", "p14", "p15"}};
    String[] groups22 = {"الشهيدة كاترين", "الأنبا كاراس السائح", "القديس البابا كيرلس السادس", "القديس البابا كيرلس عمود الدين", "الشهديان كيرياكوس و يوليطة أمة"};
    String[][] childs23 = {new String[]{"السلام لحبيب إلهنا"}, new String[]{"أبدأ بإسم الإله"}};
    String[][] childsFiles23 = {new String[]{"q1"}, new String[]{"q2"}};
    String[] groups23 = {"القديس لعازر حبيب الرب", "القديس لوقا الإنجيلي والرسول"};
    String[][] childs24 = {new String[]{"أُسبِّح بالمزمار"}, new String[]{"السلام لك يا ماربقطر", "عظيم في الأمراء"}, new String[]{"السلام لك يا مار جرجس", "أبدأ باسم الله الديان", "أفخر التعظيم وأوفر السلام", "أبدأ باسم خالق الأكوان", "أبدأ باسم الله القدوس", "أفتح فاي بالتسبيح", "تعالو يا أحباء"}, new String[]{"في مقادس العلى"}, new String[]{"مارجرجس يا مزاحم"}, new String[]{"السلام لك يا مار مرقس", "الرب الإله العظيم", "المجد والعظمة والتسبيح", "أبدأ باسم الذات العلية"}, new String[]{"أفتح فاي بالأفراح", "السلام لك يا مار مينا", "كوكب ظهر في المسيحية", "كوكب شارق في الصبحية"}, new String[]{"السلام للقديسة الشهيدة مارينا"}, new String[]{"السلام لفخر العذارى"}, new String[]{"أبدى باسم الغفور"}, new String[]{"فى مدينة قيصرية"}, new String[]{"نسبح بإيمان"}, new String[]{"فى كنيسة المنتصرين"}, new String[]{"أبدأ بنشيد ومديح"}, new String[]{"فى مدينة الانوار"}, new String[]{"العليقة التي رأها", "أمدح في البتول", "كل الطغمات السمائية", "يا أم الله القدوس", "يا م ر ي م", "يا يمامة جليلة تصيح", "خلاص أبينا آدم", "مجدك يا مريم", "عذراء يا أم الإله", "حالة الحديد:شهدت آسيا الصغرى", "السلام لك يا مريم كنز الآب المرهوب", "أبدي باسم الله العظيم", "السلام لك يا ابنة يواقيم", "طوباك يا ابنة يواقيم", "طوباك يا عذرى وبتول", "السلام للعذراء أم النور", "السلام لك يا أم الإله", "اشفعي فينا يا مريم 1", "اشفعي فينا يامريم 2", "السلام لمريم شفيعتنا", "طوباك طوباكي يا مريم", "السلام لأم الله مريم", "مريم ابنة يواقيم"}, new String[]{"أُمجِّد المسيح"}, new String[]{"أبدأ يا إخوانى"}, new String[]{"فى كنيسة الأبكار"}, new String[]{"فى كنيسة الابكار"}, new String[]{"في كواكب الفردوس", "في مجد السمائيين"}, new String[]{"أبدأ باسم الله القدوس"}, new String[]{"افرحي ايتها العروس", "هناك في الفردوس"}, new String[]{"من مدينة طيبة", "أمدح طوباوي قديس"}, new String[]{"باللحن والترنيم"}, new String[]{"في كنيسة القديسين"}, new String[]{"السلام لك يا ميخائيل", "أنا أفتح فاي وأتكلم", "أضاءت المدن والوديان", "جندي رئيس الطغمات"}, new String[]{"فى كنيسة الأبكار"}, new String[]{"فى كنيسة المنتصرين"}, new String[]{"في كنيسة الأبكار"}};
    String[][] childsFiles24 = {new String[]{"r1"}, new String[]{"r2", "r3"}, new String[]{"r4", "r5", "r6", "r7", "r8", "r9", "r10"}, new String[]{"r11"}, new String[]{"r12"}, new String[]{"r13", "r14", "r15", "r16"}, new String[]{"r17", "r18", "r19", "r20"}, new String[]{"r21"}, new String[]{"r22"}, new String[]{"r23"}, new String[]{"r24"}, new String[]{"r25"}, new String[]{"r26"}, new String[]{"r27"}, new String[]{"r28"}, new String[]{"r29", "r30", "r31", "r32", "r33", "r34", "r35", "r36", "r37", "r39", "r40", "r41", "r42", "r43", "r44", "r45", "r46", "r47", "r48", "r49", "r50", "r51", "r52"}, new String[]{"r53"}, new String[]{"r54"}, new String[]{"r55"}, new String[]{"r56"}, new String[]{"r57", "r58"}, new String[]{"r59"}, new String[]{"r60", "r61"}, new String[]{"r62", "r63"}, new String[]{"r64"}, new String[]{"r65"}, new String[]{"r66", "r67", "r68", "r69"}, new String[]{"r70"}, new String[]{"r71"}, new String[]{"r72"}};
    String[] groups24 = {"القديس مارافرام السرياني", "الشهيد ماربقطر", "الشهيد مارجرجس الروماني", "الشهيد مارجرجس السكندري", "الشهيد مارجرجس المزاحم", "الشهيد مارمرقس الإنجيلي", "الشهيد مارمينا العجائبي", "الشهيدة مارينا", "الشهيدة مارينا الناسكة", "الشهيد ماريوحنا الهرقلي", "القديسة ماكرينا", "الشهيد ماما", "القديس الانبا متاؤوس الفاخوري", "القديس متى الانجيلي و الرسول", "القديس الانبا مرقس الأنطوني", "القديسة السيدة العذراء مريم", "الشهيدة مريم الأرمنية", "القديسة مريم المصرية", "القديس مقروفيوس", "القديس الأنبا مكاريوس-أسقف قنا", "القديس الأنبا مكسيموس أسقف قويسنا والقليوبية", "القديسان مكسيموس و دوماديوس الروميان", "الشهيدة مهرائيل", "الشهيد موريس والكتيبة الطيبية", "القديس إبونا ميخائيل ابراهيم", "القديس آبونا ميخائيل البحيري", "الملاك ميخائيل رئيس الملائكه", "القديس الأنبا مينا أسقف و رئيس دير مارمينا بمريوط", "القديس مينا الصموئيلي", "الشهيد الأنبا موسى الأسود"};
    String[][] childs25 = {new String[]{"على أرضك يا رب رضيت"}};
    String[][] childsFiles25 = {new String[]{"s1"}};
    String[] groups25 = {"الشهيد نيقام"};
    String[][] childs26 = {new String[]{"هيا يا بني الإيمان"}, new String[]{"فى وسط القديسين"}};
    String[][] childsFiles26 = {new String[]{"t1"}, new String[]{"t2"}};
    String[] groups26 = {"القديس الانبا هدرا السائح الأسوانى", "القديس الأنبا هرمينا السائح"};
    String[][] childs27 = {new String[]{"أبدأ باسم الله الديان"}, new String[]{"من ترك يا إخوان"}, new String[]{"أفتح فاي بالتسبيح", "في أقصرنا شهيد صغير"}};
    String[][] childsFiles27 = {new String[]{"u1"}, new String[]{"u2"}, new String[]{"u3", "u4"}};
    String[] groups27 = {"الشهيدان سرجيوس وواخوس", "الشهيد واسيليدس الوزير", "الشهيد الانبا ونس"};
    String[][] childs28 = {new String[]{"افتح فاى بالافراح"}, new String[]{"السلام للقديسين"}, new String[]{"فى كواكب الفردوس"}, new String[]{"مع الـ24 قسيس", "في كنيسة المنتصرين"}, new String[]{"امنحي سلامك", "في الأوطان السماوية"}, new String[]{"في كورة الأحياء"}, new String[]{"السلام للعذارى"}, new String[]{"التمجيد والتسبيح"}, new String[]{"انا افتح فامى"}, new String[]{"افتح فاى بالتهليل"}, new String[]{"أبدي باسم الآب خالقنا", "فرحًا نفرح بالرب"}, new String[]{"ابدا يا احباء"}, new String[]{"في سماء ربي الحنان", "لما آن الأوان"}, new String[]{"فى صفوف السمائيين"}, new String[]{"هناك في أورشليم", "أسبح الإله الممجد"}, new String[]{"السلام ليوسف النجار خطيب العذراء المختار"}, new String[]{"إبصالية واطس"}, new String[]{"نمجد إلهنا"}, new String[]{"أبدأ باسم الإله القدوس", "اسمك جميل معناه رباني", "في حضن القديسين"}, new String[]{"مديحي في المأنوس", "أبدأ يا أخواني"}};
    String[][] childsFiles28 = {new String[]{"v1"}, new String[]{"v2"}, new String[]{"v3"}, new String[]{"v4", "v5"}, new String[]{"v6", "v7"}, new String[]{"v8"}, new String[]{"v9"}, new String[]{"v10"}, new String[]{"v11"}, new String[]{"v12"}, new String[]{"v13", "v14"}, new String[]{"v15"}, new String[]{"v16", "v17"}, new String[]{"v18"}, new String[]{"v19", "v20"}, new String[]{"v21"}, new String[]{"v22"}, new String[]{"v23"}, new String[]{"v24", "v25", "v26"}, new String[]{"v27", "v28"}};
    String[] groups28 = {"القديس الأنبا يحنس كاما", "الشهداء يسطس وأبالى وثاؤكليا", "القديس أبونا يسطس الأنطوني", "القديس ابونا يسى ميخائيل", "الشهيد يعقوب المقطع", "القديسان يواقيم وحنة", "الشهيدتان أدروسيس و يوأنة", "القديس يوحنا الانجيلى والرسول", "الشهيد يوحنا الفصيح", "القديس يوحنا فم الذهب", "أباكير ويوحنا القديسان", "القديس يوحنا القصير", "الشهيد يوحنا المعمدان", "القديس الأنبا يوساب الأبح", "الشهيدة يوستينا", "القديس يوسف النجار", "الشهيد يوليان والقديسة باسيليس", "الشهيدة بربارة والشهيدة يوليانة", "الشهديان كيرياكوس و يوليطة أمة", "الشهيد يوليوس الاقفهصي"};

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mdaya7__main);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), (NestedScrollView2) findViewById(R.id.v_nested));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_mdaya7.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
        getWindow().addFlags(128);
        new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity_mdaya7.2
            private ArrayAdapter<String> adapter7rof;
            private Spinner spinner7rof;

            @Override // java.lang.Runnable
            public void run() {
                MainActivity_mdaya7 mainActivity_mdaya7 = MainActivity_mdaya7.this;
                mainActivity_mdaya7.ctx = mainActivity_mdaya7;
                MainActivity_mdaya7.this.counterr = 0;
                MainActivity_mdaya7.this.getWindow().addFlags(128);
                new checksim();
                MainActivity_mdaya7 mainActivity_mdaya72 = MainActivity_mdaya7.this;
                mainActivity_mdaya72.t = (TextView) mainActivity_mdaya72.findViewById(R.id.gcm_msg);
                this.spinner7rof = (Spinner) MainActivity_mdaya7.this.findViewById(R.id.spinner10);
                MainActivity_mdaya7 mainActivity_mdaya73 = MainActivity_mdaya7.this;
                mainActivity_mdaya73.spinner = (Spinner) mainActivity_mdaya73.findViewById(R.id.spinner11);
                MainActivity_mdaya7 mainActivity_mdaya74 = MainActivity_mdaya7.this;
                mainActivity_mdaya74.spinner2 = (Spinner) mainActivity_mdaya74.findViewById(R.id.spinner12);
                MainActivity_mdaya7.this.spinner2.setPrompt(MainActivity_mdaya7.this.getString(R.string.syar_aba));
                MainActivity_mdaya7.this.spinner.setPrompt(MainActivity_mdaya7.this.getString(R.string.app_name));
                ArrayList arrayList = new ArrayList();
                arrayList.add("أ");
                arrayList.add("ب");
                arrayList.add("ت");
                arrayList.add("ث");
                arrayList.add("ح");
                arrayList.add("د");
                arrayList.add("ر");
                arrayList.add("ز");
                arrayList.add("س");
                arrayList.add("ش");
                arrayList.add("ص");
                arrayList.add("ع");
                arrayList.add("غ");
                arrayList.add("ف");
                arrayList.add("ق");
                arrayList.add("ك");
                arrayList.add("ل");
                arrayList.add("م");
                arrayList.add("ن");
                arrayList.add("ه");
                arrayList.add("و");
                arrayList.add("ى");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity_mdaya7.this, R.layout.spinner_item_white, arrayList);
                this.adapter7rof = arrayAdapter;
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
                this.spinner7rof.setAdapter((SpinnerAdapter) this.adapter7rof);
                this.spinner7rof.setPrompt(MainActivity_mdaya7.this.getString(R.string.characters));
                this.spinner7rof.performClick();
                this.spinner7rof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_mdaya7.2.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        MainActivity_mdaya7.this.fillDataSpinner1(i);
                        MainActivity_mdaya7.this.agynames = new String[MainActivity_mdaya7.this.listSpinner1.size()];
                        for (int i2 = 0; i2 < MainActivity_mdaya7.this.agynames.length; i2++) {
                            MainActivity_mdaya7.this.agynames[i2] = (String) MainActivity_mdaya7.this.listSpinner1.get(i2);
                        }
                        MainActivity_mdaya7.this.adapter = new ArrayAdapter<>(MainActivity_mdaya7.this, R.layout.spinner_item_white, MainActivity_mdaya7.this.agynames);
                        MainActivity_mdaya7.this.adapter.setDropDownViewResource(R.layout.spinner_item_black);
                        MainActivity_mdaya7.this.spinner.setAdapter((SpinnerAdapter) MainActivity_mdaya7.this.adapter);
                        MainActivity_mdaya7.this.spinner.setPrompt(MainActivity_mdaya7.this.getString(R.string.shohada));
                    }
                });
                MainActivity_mdaya7.this.chaptersnumbers = 0;
                MainActivity_mdaya7.this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_mdaya7.2.2
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        String[] strArr;
                        int selectedItemPosition = AnonymousClass2.this.spinner7rof.getSelectedItemPosition();
                        int selectedItemPosition2 = MainActivity_mdaya7.this.spinner.getSelectedItemPosition();
                        if (selectedItemPosition == 0) {
                            strArr = new String[MainActivity_mdaya7.this.childs1[selectedItemPosition2].length];
                            for (int i2 = 0; i2 < MainActivity_mdaya7.this.childs1[selectedItemPosition2].length; i2++) {
                                strArr[i2] = MainActivity_mdaya7.this.childs1[selectedItemPosition2][i2];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles1[selectedItemPosition2];
                        } else if (selectedItemPosition == 1) {
                            strArr = new String[MainActivity_mdaya7.this.childs2[selectedItemPosition2].length];
                            for (int i3 = 0; i3 < MainActivity_mdaya7.this.childs2[selectedItemPosition2].length; i3++) {
                                strArr[i3] = MainActivity_mdaya7.this.childs2[selectedItemPosition2][i3];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles2[selectedItemPosition2];
                        } else if (selectedItemPosition == 2) {
                            strArr = new String[MainActivity_mdaya7.this.childs3[selectedItemPosition2].length];
                            for (int i4 = 0; i4 < MainActivity_mdaya7.this.childs3[selectedItemPosition2].length; i4++) {
                                strArr[i4] = MainActivity_mdaya7.this.childs3[selectedItemPosition2][i4];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles3[selectedItemPosition2];
                        } else if (selectedItemPosition == 3) {
                            strArr = new String[MainActivity_mdaya7.this.childs4[selectedItemPosition2].length];
                            for (int i5 = 0; i5 < MainActivity_mdaya7.this.childs4[selectedItemPosition2].length; i5++) {
                                strArr[i5] = MainActivity_mdaya7.this.childs4[selectedItemPosition2][i5];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles4[selectedItemPosition2];
                        } else if (selectedItemPosition == 4) {
                            strArr = new String[MainActivity_mdaya7.this.childs6[selectedItemPosition2].length];
                            for (int i6 = 0; i6 < MainActivity_mdaya7.this.childs6[selectedItemPosition2].length; i6++) {
                                strArr[i6] = MainActivity_mdaya7.this.childs6[selectedItemPosition2][i6];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles6[selectedItemPosition2];
                        } else if (selectedItemPosition == 5) {
                            strArr = new String[MainActivity_mdaya7.this.childs8[selectedItemPosition2].length];
                            for (int i7 = 0; i7 < MainActivity_mdaya7.this.childs8[selectedItemPosition2].length; i7++) {
                                strArr[i7] = MainActivity_mdaya7.this.childs8[selectedItemPosition2][i7];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles8[selectedItemPosition2];
                        } else if (selectedItemPosition == 6) {
                            strArr = new String[MainActivity_mdaya7.this.childs10[selectedItemPosition2].length];
                            for (int i8 = 0; i8 < MainActivity_mdaya7.this.childs10[selectedItemPosition2].length; i8++) {
                                strArr[i8] = MainActivity_mdaya7.this.childs10[selectedItemPosition2][i8];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles10[selectedItemPosition2];
                        } else if (selectedItemPosition == 7) {
                            strArr = new String[MainActivity_mdaya7.this.childs11[selectedItemPosition2].length];
                            for (int i9 = 0; i9 < MainActivity_mdaya7.this.childs11[selectedItemPosition2].length; i9++) {
                                strArr[i9] = MainActivity_mdaya7.this.childs11[selectedItemPosition2][i9];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles11[selectedItemPosition2];
                        } else if (selectedItemPosition == 8) {
                            strArr = new String[MainActivity_mdaya7.this.childs12[selectedItemPosition2].length];
                            for (int i10 = 0; i10 < MainActivity_mdaya7.this.childs12[selectedItemPosition2].length; i10++) {
                                strArr[i10] = MainActivity_mdaya7.this.childs12[selectedItemPosition2][i10];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles12[selectedItemPosition2];
                        } else if (selectedItemPosition == 9) {
                            strArr = new String[MainActivity_mdaya7.this.childs13[selectedItemPosition2].length];
                            for (int i11 = 0; i11 < MainActivity_mdaya7.this.childs13[selectedItemPosition2].length; i11++) {
                                strArr[i11] = MainActivity_mdaya7.this.childs13[selectedItemPosition2][i11];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles13[selectedItemPosition2];
                        } else if (selectedItemPosition == 10) {
                            strArr = new String[MainActivity_mdaya7.this.childs14[selectedItemPosition2].length];
                            for (int i12 = 0; i12 < MainActivity_mdaya7.this.childs14[selectedItemPosition2].length; i12++) {
                                strArr[i12] = MainActivity_mdaya7.this.childs14[selectedItemPosition2][i12];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles14[selectedItemPosition2];
                        } else if (selectedItemPosition == 11) {
                            strArr = new String[MainActivity_mdaya7.this.childs18[selectedItemPosition2].length];
                            for (int i13 = 0; i13 < MainActivity_mdaya7.this.childs18[selectedItemPosition2].length; i13++) {
                                strArr[i13] = MainActivity_mdaya7.this.childs18[selectedItemPosition2][i13];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles18[selectedItemPosition2];
                        } else if (selectedItemPosition == 12) {
                            strArr = new String[MainActivity_mdaya7.this.childs19[selectedItemPosition2].length];
                            for (int i14 = 0; i14 < MainActivity_mdaya7.this.childs19[selectedItemPosition2].length; i14++) {
                                strArr[i14] = MainActivity_mdaya7.this.childs19[selectedItemPosition2][i14];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles19[selectedItemPosition2];
                        } else if (selectedItemPosition == 13) {
                            strArr = new String[MainActivity_mdaya7.this.childs20[selectedItemPosition2].length];
                            for (int i15 = 0; i15 < MainActivity_mdaya7.this.childs20[selectedItemPosition2].length; i15++) {
                                strArr[i15] = MainActivity_mdaya7.this.childs20[selectedItemPosition2][i15];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles20[selectedItemPosition2];
                        } else if (selectedItemPosition == 14) {
                            strArr = new String[MainActivity_mdaya7.this.childs21[selectedItemPosition2].length];
                            for (int i16 = 0; i16 < MainActivity_mdaya7.this.childs21[selectedItemPosition2].length; i16++) {
                                strArr[i16] = MainActivity_mdaya7.this.childs21[selectedItemPosition2][i16];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles21[selectedItemPosition2];
                        } else if (selectedItemPosition == 15) {
                            strArr = new String[MainActivity_mdaya7.this.childs22[selectedItemPosition2].length];
                            for (int i17 = 0; i17 < MainActivity_mdaya7.this.childs22[selectedItemPosition2].length; i17++) {
                                strArr[i17] = MainActivity_mdaya7.this.childs22[selectedItemPosition2][i17];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles22[selectedItemPosition2];
                        } else if (selectedItemPosition == 16) {
                            strArr = new String[MainActivity_mdaya7.this.childs23[selectedItemPosition2].length];
                            for (int i18 = 0; i18 < MainActivity_mdaya7.this.childs23[selectedItemPosition2].length; i18++) {
                                strArr[i18] = MainActivity_mdaya7.this.childs23[selectedItemPosition2][i18];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles23[selectedItemPosition2];
                        } else if (selectedItemPosition == 17) {
                            strArr = new String[MainActivity_mdaya7.this.childs24[selectedItemPosition2].length];
                            for (int i19 = 0; i19 < MainActivity_mdaya7.this.childs24[selectedItemPosition2].length; i19++) {
                                strArr[i19] = MainActivity_mdaya7.this.childs24[selectedItemPosition2][i19];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles24[selectedItemPosition2];
                        } else if (selectedItemPosition == 18) {
                            strArr = new String[MainActivity_mdaya7.this.childs25[selectedItemPosition2].length];
                            for (int i20 = 0; i20 < MainActivity_mdaya7.this.childs25[selectedItemPosition2].length; i20++) {
                                strArr[i20] = MainActivity_mdaya7.this.childs25[selectedItemPosition2][i20];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles25[selectedItemPosition2];
                        } else if (selectedItemPosition == 19) {
                            strArr = new String[MainActivity_mdaya7.this.childs26[selectedItemPosition2].length];
                            for (int i21 = 0; i21 < MainActivity_mdaya7.this.childs26[selectedItemPosition2].length; i21++) {
                                strArr[i21] = MainActivity_mdaya7.this.childs26[selectedItemPosition2][i21];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles26[selectedItemPosition2];
                        } else if (selectedItemPosition == 20) {
                            strArr = new String[MainActivity_mdaya7.this.childs27[selectedItemPosition2].length];
                            for (int i22 = 0; i22 < MainActivity_mdaya7.this.childs27[selectedItemPosition2].length; i22++) {
                                strArr[i22] = MainActivity_mdaya7.this.childs27[selectedItemPosition2][i22];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles27[selectedItemPosition2];
                        } else if (selectedItemPosition == 21) {
                            strArr = new String[MainActivity_mdaya7.this.childs28[selectedItemPosition2].length];
                            for (int i23 = 0; i23 < MainActivity_mdaya7.this.childs28[selectedItemPosition2].length; i23++) {
                                strArr[i23] = MainActivity_mdaya7.this.childs28[selectedItemPosition2][i23];
                            }
                            MainActivity_mdaya7.this.selectedSubChild = MainActivity_mdaya7.this.childsFiles28[selectedItemPosition2];
                        } else {
                            strArr = null;
                        }
                        MainActivity_mdaya7.this.setspinn(strArr);
                        MainActivity_mdaya7.this.chaptersnumbers = 0;
                    }
                });
                MainActivity_mdaya7.this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_mdaya7.2.3
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) throws IOException {
                        MainActivity_mdaya7.this.fillData(MainActivity_mdaya7.this.selectedSubChild[i]);
                    }
                });
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillData(String str) throws IOException {
        try {
            InputStream inputStreamOpen = getAssets().open("mdaya7/" + str + ".html");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String str2 = new String(bArr);
            WebView webView = (WebView) findViewById(R.id.webView1);
            this.myWebView = webView;
            webView.getSettings().setBuiltInZoomControls(true);
            this.myWebView.setWebViewClient(new myWebClient());
            WebSettings settings = this.myWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSupportZoom(true);
            this.myWebView.loadDataWithBaseURL("", str2.replaceAll("#000000", "#FFFFFF").replaceAll("#F00", "FFFFFFF"), "text/html", "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class myWebClient extends WebViewClient {
        public myWebClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
        }
    }

    public void setspinn(String[] strArr) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, strArr);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner2.setAdapter((SpinnerAdapter) this.adapterglav);
        this.spinner2.setPrompt(getString(R.string.hello_world));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        AppLeftSlider.setCurrentActivity(this);
        super.onResume();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        GestureDetector gestureDetector = this.gestureDetector2;
        if (gestureDetector == null || !gestureDetector.onTouchEvent(motionEvent)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.gestureDetector2.onTouchEvent(motionEvent);
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        private SwipeDetector() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (Math.abs(motionEvent.getY() - motionEvent2.getY()) > 250.0f) {
                return false;
            }
            if (motionEvent.getX() - motionEvent2.getX() > 120.0f && Math.abs(f) > 200.0f) {
                Log.d("log", "left");
                if (MainActivity_mdaya7.this.spinner2.getSelectedItemPosition() > 0 && MainActivity_mdaya7.this.spinner2.getCount() >= 1) {
                    MainActivity_mdaya7.this.spinner.getSelectedItemPosition();
                    MainActivity_mdaya7.this.spinner2.setSelection(MainActivity_mdaya7.this.spinner2.getSelectedItemPosition() - 1);
                }
                return true;
            }
            if (motionEvent2.getX() - motionEvent.getX() <= 120.0f || Math.abs(f) <= 200.0f) {
                return false;
            }
            Log.d("log", "right");
            if (MainActivity_mdaya7.this.spinner2.getSelectedItemPosition() < MainActivity_mdaya7.this.spinner2.getCount() - 1 && MainActivity_mdaya7.this.spinner2.getCount() > 1) {
                MainActivity_mdaya7.this.spinner.getSelectedItemPosition();
                MainActivity_mdaya7.this.spinner2.setSelection(MainActivity_mdaya7.this.spinner2.getSelectedItemPosition() + 1);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillDataSpinner1(int i) {
        this.listSpinner1 = new ArrayList<>();
        int i2 = 0;
        if (i == 0) {
            while (true) {
                String[] strArr = this.groups1;
                if (i2 >= strArr.length) {
                    return;
                }
                this.listSpinner1.add(strArr[i2]);
                i2++;
            }
        } else if (i == 1) {
            while (true) {
                String[] strArr2 = this.groups2;
                if (i2 >= strArr2.length) {
                    return;
                }
                this.listSpinner1.add(strArr2[i2]);
                i2++;
            }
        } else if (i == 2) {
            while (true) {
                String[] strArr3 = this.groups3;
                if (i2 >= strArr3.length) {
                    return;
                }
                this.listSpinner1.add(strArr3[i2]);
                i2++;
            }
        } else if (i == 3) {
            while (true) {
                String[] strArr4 = this.groups4;
                if (i2 >= strArr4.length) {
                    return;
                }
                this.listSpinner1.add(strArr4[i2]);
                i2++;
            }
        } else if (i == 4) {
            while (true) {
                String[] strArr5 = this.groups6;
                if (i2 >= strArr5.length) {
                    return;
                }
                this.listSpinner1.add(strArr5[i2]);
                i2++;
            }
        } else if (i == 5) {
            while (true) {
                String[] strArr6 = this.groups8;
                if (i2 >= strArr6.length) {
                    return;
                }
                this.listSpinner1.add(strArr6[i2]);
                i2++;
            }
        } else if (i == 6) {
            while (true) {
                String[] strArr7 = this.groups10;
                if (i2 >= strArr7.length) {
                    return;
                }
                this.listSpinner1.add(strArr7[i2]);
                i2++;
            }
        } else if (i == 7) {
            while (true) {
                String[] strArr8 = this.groups11;
                if (i2 >= strArr8.length) {
                    return;
                }
                this.listSpinner1.add(strArr8[i2]);
                i2++;
            }
        } else if (i == 8) {
            while (true) {
                String[] strArr9 = this.groups12;
                if (i2 >= strArr9.length) {
                    return;
                }
                this.listSpinner1.add(strArr9[i2]);
                i2++;
            }
        } else if (i == 9) {
            while (true) {
                String[] strArr10 = this.groups13;
                if (i2 >= strArr10.length) {
                    return;
                }
                this.listSpinner1.add(strArr10[i2]);
                i2++;
            }
        } else if (i == 10) {
            while (true) {
                String[] strArr11 = this.groups14;
                if (i2 >= strArr11.length) {
                    return;
                }
                this.listSpinner1.add(strArr11[i2]);
                i2++;
            }
        } else if (i == 11) {
            while (true) {
                String[] strArr12 = this.groups18;
                if (i2 >= strArr12.length) {
                    return;
                }
                this.listSpinner1.add(strArr12[i2]);
                i2++;
            }
        } else if (i == 12) {
            while (true) {
                String[] strArr13 = this.groups19;
                if (i2 >= strArr13.length) {
                    return;
                }
                this.listSpinner1.add(strArr13[i2]);
                i2++;
            }
        } else if (i == 13) {
            while (true) {
                String[] strArr14 = this.groups20;
                if (i2 >= strArr14.length) {
                    return;
                }
                this.listSpinner1.add(strArr14[i2]);
                i2++;
            }
        } else if (i == 14) {
            while (true) {
                String[] strArr15 = this.groups21;
                if (i2 >= strArr15.length) {
                    return;
                }
                this.listSpinner1.add(strArr15[i2]);
                i2++;
            }
        } else if (i == 15) {
            while (true) {
                String[] strArr16 = this.groups22;
                if (i2 >= strArr16.length) {
                    return;
                }
                this.listSpinner1.add(strArr16[i2]);
                i2++;
            }
        } else if (i == 16) {
            while (true) {
                String[] strArr17 = this.groups23;
                if (i2 >= strArr17.length) {
                    return;
                }
                this.listSpinner1.add(strArr17[i2]);
                i2++;
            }
        } else if (i == 17) {
            while (true) {
                String[] strArr18 = this.groups24;
                if (i2 >= strArr18.length) {
                    return;
                }
                this.listSpinner1.add(strArr18[i2]);
                i2++;
            }
        } else if (i == 18) {
            while (true) {
                String[] strArr19 = this.groups25;
                if (i2 >= strArr19.length) {
                    return;
                }
                this.listSpinner1.add(strArr19[i2]);
                i2++;
            }
        } else if (i == 19) {
            while (true) {
                String[] strArr20 = this.groups26;
                if (i2 >= strArr20.length) {
                    return;
                }
                this.listSpinner1.add(strArr20[i2]);
                i2++;
            }
        } else if (i == 20) {
            while (true) {
                String[] strArr21 = this.groups27;
                if (i2 >= strArr21.length) {
                    return;
                }
                this.listSpinner1.add(strArr21[i2]);
                i2++;
            }
        } else {
            if (i != 21) {
                return;
            }
            while (true) {
                String[] strArr22 = this.groups28;
                if (i2 >= strArr22.length) {
                    return;
                }
                this.listSpinner1.add(strArr22[i2]);
                i2++;
            }
        }
    }
}
