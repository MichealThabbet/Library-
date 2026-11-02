window.onscroll = function() {myFunction()};

function myFunction() {
  var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
  var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
  var scrolled = (winScroll / height) * 100;
  document.getElementById("myBar").style.width = scrolled + "%";
}
$('input:checkbox').change(function(){
   var col_name = $(this).attr("name");    
    if(this.checked){
        $("td[class='" + col_name + "']").show();
        $("td[id='" + col_name + "']").show();   
		
    }else{
        $("td[class='" + col_name + "']").hide();
        $("td[id='" + col_name + "']").hide();

    }
});
function showhide_div() {
  var checkBox = document.getElementById("myCheck");
  if (checkBox.checked == true){
    hidden_div.style.display = "block";
    hidden_div2.style.display = "block";	
	hidden_div3.style.display = "block";
	hidden_div4.style.display = "block";
	hidden_div5.style.display = "block";
	hidden_div6.style.display = "block";
	hidden_div7.style.display = "block";
	hidden_div8.style.display = "block";
	hidden_div9.style.display = "block";
	hidden_div10.style.display = "block";
	hidden_div11.style.display = "block";
  } else {
    hidden_div.style.display = "none";
    hidden_div2.style.display = "none";
	hidden_div3.style.display = "none";
	hidden_div4.style.display = "none";
    hidden_div5.style.display = "none";
	hidden_div6.style.display = "none";
	hidden_div7.style.display = "none";
    hidden_div8.style.display = "none";
	hidden_div9.style.display = "none";
	hidden_div10.style.display = "none";
	hidden_div11.style.display = "none";	
  }
}

function showhide_div2() {
  var x = document.getElementById("hidden_div hidden_div2");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function goryfunc() {
    document.getElementById("Col1").style.display= "none";
    document.getElementById("Col2").style.display= "none";
}
function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}


	function showKodas(select){
	document.getElementById('bas_S1A').style.display = "none"
	document.getElementById('bas_S1B').style.display = "none"
	document.getElementById('bas_S2A').style.display = "none"
	document.getElementById('bas_S2B').style.display = "none"
	document.getElementById('gor_S2A').style.display = "none"
	document.getElementById('gor_S2B').style.display = "none"
	document.getElementById('gor_S1A').style.display = "none"
	document.getElementById('gor_S1B').style.display = "none"
    document.getElementById('ker_S1A').style.display = "none"
	document.getElementById('ker_S1B').style.display = "none"
	document.getElementById('ker_S2A').style.display = "none"
	document.getElementById('ker_S2B').style.display = "none"
	document.getElementById('gory_menu').style.display = "none";
	document.getElementById('basely_menu').style.display = "none";
	document.getElementById('ker_menu').style.display = "none";
	document.getElementById('buttongory_solh').style.display = "none";
	document.getElementById('buttonbas_solh').style.display = "none";
	document.getElementById('buttonker_solh').style.display = "none";
	document.getElementById('bas_M1').style.display = "none"
	document.getElementById('gor_M1').style.display = "none"
	document.getElementById('bas_M2').style.display = "none"
	document.getElementById('gor_M2').style.display = "none"
	document.getElementById('tlba_P1').style.display = "none"
	document.getElementById('tlba_P2').style.display = "none"
	document.getElementById('buttontlba').style.display = "none"
	document.getElementById('erhamna').style.display = "none"
	document.getElementById('osh_B1').style.display = "none"
	document.getElementById('osh_M1').style.display = "none"
	document.getElementById('bas').style.display = "none"
	document.getElementById('bas_M3').style.display = "none"
	document.getElementById('gor_M3').style.display = "none"
        if(select.value=='basely'){
    document.getElementById('basely_menu').style.display = "block";
	document.getElementById('buttonbas_solh').style.display = "block";
	document.getElementById('bas_S1A').style.display = "block"
	document.getElementById('bas_S1B').style.display = "block"
	document.getElementById("buttonbas_solh").innerHTML = "عال فوق"
	document.getElementById('bas_M1').style.display = "block"
	document.getElementById('bas_M2').style.display = "block"
	document.getElementById('osh_B1').style.display = "block"
	document.getElementById('bas').style.display = "block"
	document.getElementById("buttontlba").innerHTML = "الطلبة وارحمنا"
	document.getElementById('bas_M3').style.display = "block"
   } else    if(select.value=='gory'){
	document.getElementById('gory_menu').style.display = "block";
	document.getElementById('buttongory_solh').style.display = "block";
	document.getElementById('gor_S1A').style.display = "block"
	document.getElementById('gor_S1B').style.display = "block"
	document.getElementById('gor_M1').style.display = "block"
	document.getElementById('gor_M2').style.display = "block"
	document.getElementById('tlba_P1').style.display = "block"
	document.getElementById('tlba_P2').style.display = "block"
	document.getElementById('erhamna').style.display = "block"
	document.getElementById('osh_M1').style.display = "block"
	document.getElementById('gor_M3').style.display = "block"
   }  else    if(select.value=='kerolos'){
	document.getElementById('ker_menu').style.display = "block";
	document.getElementById("buttonker_solh").innerHTML = "يا اله المحبة"
	document.getElementById('buttonker_solh').style.display = "block";
	document.getElementById('ker_S1A').style.display = "block"
	document.getElementById('ker_S1B').style.display = "block"
   }

}

function basely_solh() {
  var x = document.getElementById("buttonbas_solh");
  	document.getElementById('bas_S1A').style.display = "none"
	document.getElementById('bas_S1B').style.display = "none"
	document.getElementById('bas_S2A').style.display = "none"
	document.getElementById('bas_S2B').style.display = "none"
  if (x.innerHTML === "عال فوق") {
    x.innerHTML = "يا الله العظيم"
	document.getElementById('bas_S2A').style.display = "block"
	document.getElementById('bas_S2B').style.display = "block"
  } else {
    x.innerHTML = "عال فوق";
	document.getElementById('bas_S1A').style.display = "block"
	document.getElementById('bas_S1B').style.display = "block"

  }
}

function gory_Solh() {
  var x = document.getElementById("buttongory_solh");
  	document.getElementById('gor_S1A').style.display = "none"
	document.getElementById('gor_S1B').style.display = "none"
	document.getElementById('gor_S2A').style.display = "none"
	document.getElementById('gor_S2B').style.display = "none"
  if (x.innerHTML === "أيها المسيح إلهنا") {
    x.innerHTML = "ايها الكائن";
	document.getElementById('gor_S2A').style.display = "block"
	document.getElementById('gor_S2B').style.display = "block"
  } else {
    x.innerHTML = "أيها المسيح إلهنا";
	document.getElementById('gor_S1A').style.display = "block"
	document.getElementById('gor_S1B').style.display = "block"
  }
}

function ker_Solh() {
  var x = document.getElementById("buttonker_solh");
  	document.getElementById('ker_S1A').style.display = "none"
	document.getElementById('ker_S1B').style.display = "none"
	document.getElementById('ker_S2A').style.display = "none"
	document.getElementById('ker_S2B').style.display = "none"
  if (x.innerHTML === "يا اله المحبة") {
    x.innerHTML = "يا رئيس الحياة";
	document.getElementById('ker_S2A').style.display = "block"
	document.getElementById('ker_S2B').style.display = "block"
  } else {
    x.innerHTML = "يا اله المحبة";
	document.getElementById('ker_S1A').style.display = "block"
	document.getElementById('ker_S1B').style.display = "block"
  }
}

function tlba() {
  var x = document.getElementById("buttontlba");
  	document.getElementById('tlba_P1').style.display = "none"
	document.getElementById('tlba_P2').style.display = "none"
	document.getElementById('erhamna').style.display = "none"
  if (x.innerHTML === "اخفاء الطلبة") {
    x.innerHTML = "الطلبة";
  } else {
    x.innerHTML = "اخفاء الطلبة";
	document.getElementById('tlba_P1').style.display = "block"
	document.getElementById('tlba_P2').style.display = "block"
	document.getElementById('erhamna').style.display = "block"
  }
}
function tlba1() {
  var x = document.getElementById("buttontlba_1");
  	document.getElementById('tlba_P1').style.display = "none"
	document.getElementById('tlba_P2').style.display = "none"
  if (x.innerHTML === "اخفاء الطلبة") {
    x.innerHTML = "الطلبة";
	document.getElementById("buttontlba_2").innerHTML = "الطلبة";
  } else {
    x.innerHTML = "اخفاء الطلبة";
	document.getElementById('tlba_P1').style.display = "block"
	document.getElementById('tlba_P2').style.display = "block"
	document.getElementById("buttontlba_2").innerHTML = "اخفاء الطلبة";
  }
}

function tlba2() {
  var x = document.getElementById("buttontlba_2");
  	document.getElementById('tlba_P1').style.display = "none"
	document.getElementById('tlba_P2').style.display = "none"
  if (x.innerHTML === "اخفاء الطلبة") {
    x.innerHTML = "الطلبة";
	document.getElementById("buttontlba_1").innerHTML = "الطلبة";
  } else {
    x.innerHTML = "اخفاء الطلبة";
	document.getElementById('tlba_P1').style.display = "block"
	document.getElementById('tlba_P2').style.display = "block"
	document.getElementById("buttontlba_1").innerHTML = "اخفاء الطلبة";
  }
}
function tlba3() {
  var x = document.getElementById("buttontlba_3");
	document.getElementById('erhamna').style.display = "none"
  if (x.innerHTML === "اخفاء لحن ارحمنا") {
    x.innerHTML = "لحن ارحمنا ";
  } else {
    x.innerHTML = "اخفاء لحن ارحمنا";
	document.getElementById('erhamna').style.display = "block"
  }
}

function dec_espabig() {
  var x = document.getElementById("buttondeacon_esbabig");
	document.getElementById('dec_espa').style.display = "none"
  if (x.innerHTML === "اخفاء: أسبازيستي الكبيرة") {
    x.innerHTML = "أسبازيستي الكبيرة";
  } else {
    x.innerHTML = "اخفاء: أسبازيستي الكبيرة";
	document.getElementById('dec_espa').style.display = "block"
  }
}

function osh_water() {
  var x = document.getElementById("osh_water");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function osh_grass() {
  var x = document.getElementById("osh_grass");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function osh_food() {
  var x = document.getElementById("osh_food");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

$(document).ready(function(){
	  var x = document.getElementById("kesma").selected;
    $("select").change(function(){
        $(this).find("option:selected").each(function(){
            var optionValue = $(this).attr("value");
            if(optionValue){
                $(".box").not("." + optionValue).hide();
                $("." + optionValue).show();
            } else{
                $(".box").hide();
            }
        });
    }).change();
});

function showBaptism(select){
	document.getElementById('male_s1').style.display = "none"
	document.getElementById('male_s2').style.display = "none"
	document.getElementById('male_s3').style.display = "none"
	document.getElementById('male_s4').style.display = "none"
	document.getElementById('female_s1').style.display = "none"
	document.getElementById('female_s2').style.display = "none"	
	document.getElementById('female_s3').style.display = "none"	
	document.getElementById('female_s4').style.display = "none"	
        if(select.value=='male'){
   	document.getElementById('male_s1').style.display = "block"
	document.getElementById('male_s2').style.display = "block"
	document.getElementById('male_s3').style.display = "block"
	document.getElementById('male_s4').style.display = "block"
   } else    if(select.value=='female'){
	document.getElementById('female_s1').style.display = "block"
	document.getElementById('female_s2').style.display = "block"
	document.getElementById('female_s3').style.display = "block"
	document.getElementById('female_s4').style.display = "block"
   }  

}

function showFuneral(select){
	document.getElementById('male_s1').style.display = "none"
	document.getElementById('male_s2').style.display = "none"
	document.getElementById('male_s3').style.display = "none"
	document.getElementById('male_s4').style.display = "none"
	document.getElementById('male_s5').style.display = "none"
	document.getElementById('female_s1').style.display = "none"
	document.getElementById('female_s2').style.display = "none"	
	document.getElementById('female_s3').style.display = "none"	
	document.getElementById('female_s4').style.display = "none"	
	document.getElementById('female_s5').style.display = "none"	
	document.getElementById('birth_s1').style.display = "none"
	document.getElementById('birth_s2').style.display = "none"
	document.getElementById('birth_s3').style.display = "none"
	document.getElementById('birth_s4').style.display = "none"
	document.getElementById('boy_s1').style.display = "none"
	document.getElementById('boy_s2').style.display = "none"
	document.getElementById('boy_s3').style.display = "none"
	document.getElementById('boy_s4').style.display = "none"
	document.getElementById('boy_s5').style.display = "none"
	document.getElementById('girl_s1').style.display = "none"
	document.getElementById('girl_s2').style.display = "none"
	document.getElementById('girl_s3').style.display = "none"
	document.getElementById('girl_s4').style.display = "none"
	document.getElementById('girl_s5').style.display = "none"
        if(select.value=='f_male'){
   	document.getElementById('male_s1').style.display = "block"
	document.getElementById('male_s2').style.display = "block"
	document.getElementById('male_s3').style.display = "block"
	document.getElementById('male_s4').style.display = "block"
	document.getElementById('male_s5').style.display = "block"
   } else    if(select.value=='f_female'){
	document.getElementById('female_s1').style.display = "block"
	document.getElementById('female_s2').style.display = "block"
	document.getElementById('female_s3').style.display = "block"
	document.getElementById('female_s4').style.display = "block"
	document.getElementById('female_s5').style.display = "block"
      } else    if(select.value=='f_birth'){
	document.getElementById('birth_s1').style.display = "block"
	document.getElementById('birth_s2').style.display = "block"
	document.getElementById('birth_s3').style.display = "block"
	document.getElementById('birth_s4').style.display = "block"
	document.getElementById('female_s5').style.display = "block"
   }    else    if(select.value=='f_boy'){
	document.getElementById('boy_s1').style.display = "block"
	document.getElementById('boy_s2').style.display = "block"
	document.getElementById('boy_s3').style.display = "block"
	document.getElementById('boy_s4').style.display = "block"
	document.getElementById('boy_s5').style.display = "block"
   
      }    else    if(select.value=='f_girl'){
	document.getElementById('girl_s1').style.display = "block"
	document.getElementById('girl_s2').style.display = "block"
	document.getElementById('girl_s3').style.display = "block"
	document.getElementById('girl_s4').style.display = "block"
	document.getElementById('girl_s5').style.display = "block"
   }


}

function showtomb(select){
	document.getElementById('male_s1').style.display = "none"
	document.getElementById('female_s1').style.display = "none"
        if(select.value=='male'){
   	document.getElementById('male_s1').style.display = "block"
   } else    if(select.value=='female'){
	document.getElementById('female_s1').style.display = "block"
      }


}

function showhide_info() {
  var x = document.getElementById("info");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function showhide_hazzat() {
  var x = document.getElementById("hazzat");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function showhide_tamol() {
  var x = document.getElementById("tamol");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}


function showhide_img() {
	
  var images = document.querySelectorAll('img');
  images.forEach(function(img) {
	  
	   if (img.style.display === "none") {
    img.style.display = "block";
  } else {
    img.style.display = "none";
  }
	 
  });
}
	
	
	
	
	
	




$(document).ready(function(){
  // Add smooth scrolling to all links
  $("a").on('click', function(event) {

    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 800, function(){
   
        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
});

