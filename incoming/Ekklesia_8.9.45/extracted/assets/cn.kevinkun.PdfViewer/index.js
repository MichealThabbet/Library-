//PDFJS.cMapUrl = 'https://unpkg.com/pdfjs-dist@1.9.426/cmaps/';
//PDFJS.cMapPacked = true;
var pdfDoc = null;
var canvases = [];
function loadPdf(url){
	PDFJS.getDocument(url).then(function(pdf) {
		pdfDoc = pdf;
				console.log(pdf.numPages);
		if (typeof(PdfViewer) != "undefined") {
			PdfViewer.pdfReady(pdfDoc.numPages);
		}
	}).catch(function(error){
		console.log(error.message);
		if (typeof(PdfViewer) != "undefined") {
			PdfViewer.error(error.message);
		}
    });
}

function getParam(variable) {
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		if (pair[0] == variable) {
			return pair[1];
		}
	}
	return (false);
}


function renderPage(num) {
    
	pdfDoc.getPage(num).then(function(page) {
		
		
		var viewport = page.getViewport(2.0);
		var canvas = document.createElement("canvas");
		canvas.setAttribute("id",num);
		canvases.push(canvas);
		document.body.appendChild(canvas);
		var ctx = canvas.getContext('2d');

		canvas.height = viewport.height;
		canvas.width = viewport.width;

		page.render({
			canvasContext: ctx,
			viewport: viewport
		});
		
		var seperater = document.createElement("div");
		document.body.appendChild(seperater);
		
	}).catch(function(error){
		console.log(error.message);
		if (typeof(PdfViewer) != "undefined") {
			PdfViewer.error(error.message);
		}
    });
}

function goto(page){
		let element = document.getElementById(page);
		if (element) {
		   element.scrollIntoView({ behavior: 'smooth' });
		}

	}


function preview(p) {
	if (pdfDoc) {
		var temp = p.split(",");
		var pages = [];

		for (var i = 0; i < temp.length; i++) {
			if (temp[i].indexOf("-") != -1) {
				var temp2 = temp[i].split("-");
				if (temp2[0] == "") {
					temp2[0] = 1;
				}
				if (temp2[1] == "") {
					temp2[1] = pdfDoc.numPages;
				}
				for (var j = temp2[0] * 1; j <= temp2[1] * 1; j++) {
					if(pages.indexOf(j)==-1){
						pages.push(j);
					}
					
				}
			} else {
				if(pages.indexOf(temp[i] * 1)==-1){
					pages.push(temp[i] * 1);
				}
				
			}

		}
		document.body.innerHTML="";
		canvases = [];
		lastVisiblePage = -1;
		for (var i = 0; i < pages.length; i++) {

			if (pages[i] > 0 && pages[i] <= pdfDoc.numPages) {

				renderPage(pages[i]);
			}
		}
	}
}

	function isInView(el) {
		var rect = el.getBoundingClientRect();
		var elemTop = rect.top;
		var elemHeight = rect.height;
		var elemBottom = rect.bottom;
	 
		// Only completely visible elements return true:
		//var isVisible = (elemTop+elemHeight/2 >= 0) && (elemBottom <= window.innerHeight);
		// Partially visible elements return true:
		isVisible = elemTop < window.innerHeight && elemBottom >= 0;
		return isVisible;
	}
	
	
	var lastVisiblePage = -1;
	window.onscroll = function printVisible(){
		var visibleId = -1;
		for(var i =0; i < canvases.length; i++){
			if(isInView(canvases[i])){
				visibleId = canvases[i].id;
				break;
			}
		}

		if(lastVisiblePage != visibleId){
			lastVisiblePage = visibleId;
			console.log(visibleId);
			if (typeof(PdfViewer) != "undefined") {
				PdfViewer.scrolled(visibleId);
			}
		}

	};