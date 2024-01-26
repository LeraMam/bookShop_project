var RGBChange = function() {
	$('#RGB').css('background', 'rgb('+r.getValue()+','+g.getValue()+','+b.getValue()+')')
};	
	  
/*scroll to top*/

$(document).ready(function(){
	$(function () {
		$.scrollUp({
	        scrollName: 'scrollUp', // ID элемента
	        scrollDistance: 300, // расстояние пока не покажется значок
	        scrollFrom: 'top',
	        scrollSpeed: 300,
	        easingType: 'linear',
	        animation: 'fade',
	        animationSpeed: 200,

	        scrollTrigger: false, 
	        scrollText: '<i class="fa fa-angle-up"></i>', //стиль элемента
	        scrollTitle: false, 
	        scrollImg: false,
	        activeOverlay: false, 
	        zIndex: 2147483647
		});
	});
});

function showMessage(message, timeout = 2000, afterMessageAction= ()=>{}) {
    const x = document.getElementById("snackbar");
    x.innerHTML = message;
    x.className = "show";
    setTimeout(function () {
        x.className = x.className.replace("show", "");
        afterMessageAction()
    }, timeout);
}