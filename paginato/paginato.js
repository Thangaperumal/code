function paginatoInit(blk) {
	$('.'+blk).html('<a class="rs-page-navigation-first" href="javascript:void(0)" onclick="paginatoNavigate(this)">'+paginato.firstTxt+'</a> <a class="rs-page-navigation-previous" href="javascript:void(0)" onclick="paginatoNavigate(this)">'+paginato.prevTxt+'</a> ');
	for(i=1;i<=paginato.pagevisble;i++) {
		addcls='';
		if (i==paginato.curPage) {
			addcls=' selected-page';
		}
		$('.'+blk).append('<a class="rs-page-navigation-page'+addcls+'" href="javascript:void(0)" onclick="paginatoNavigate(this)">'+i+'</a> ');
	}
	$('.'+blk).append('<a class="rs-page-navigation-next" href="javascript:void(0)" onclick="paginatoNavigate(this)">'+paginato.nextTxt+'</a> <a class="rs-page-navigation-last" href="javascript:void(0)" onclick="paginatoNavigate(this)">'+paginato.lastTxt+'</a>');
}


function paginatoRearrange(currentpage) {
    var middleNu = Math.floor(paginato.pagevisble/2);
    var pageshand=$("a.rs-page-navigation-page");
    pageshand.show();
    if (parseInt(paginato.totalPages) < parseInt(paginato.pagevisble)) {
        pageshand.each(function() {
        if ($(this).text() > paginato.totalPages) $(this).hide();
        })
    }
    else if (currentpage > paginato.totalPages-(paginato.pagevisble-1)){
	for(i=0,j=paginato.pagevisble-1;i<paginato.pagevisble;i++,j--) {
        $(pageshand[i]).text(paginato.totalPages-j);
 	}
    }
    else if (currentpage > middleNu) {
	for(i=0,j=-(middleNu);i<paginato.pagevisble;i++,j++) {
		$(pageshand[i]).text(parseInt(currentpage)+j);
	}
    }
    else if (currentpage <= middleNu) {
	for(i=0;i<paginato.pagevisble;i++) {
		$(pageshand[i]).text(i+1);
	}
    }  

    pageshand.each(function() {
        if ($(this).text() == currentpage) { 
            $(this).addClass("selected-page"); 
        }else {
            $(this).removeClass("selected-page"); 
        }
    })
}

function paginatoNavigate(hand) {
    var curVal = $(hand).text();
    if (curVal == paginato.firstTxt) {
        curVal = 1;
    }
    else if (curVal == paginato.prevTxt) {
        if (paginato.curPage>1) { 
            curVal = paginato.curPage - 1; 
        }
        else {
            curVal = -1;
        }
    }
    else if (curVal == paginato.nextTxt) {
        if (paginato.curPage<paginato.totalPages) { 
            curVal = parseInt(paginato.curPage) + 1; 
        }
        else {
            curVal = -1;
        }
    }
    else if (curVal == paginato.lastTxt) {
        curVal = paginato.totalPages;
    }

    if (curVal != -1) {
        makeYourAjaxCall(curVal);
        paginatoRearrange(curVal);
        paginato.curPage = curVal;
    }
}
