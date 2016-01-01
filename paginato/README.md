Paginato
=========

It gives the simple code to implement a pagination with jQuery and Ajax

To-Do
=========

To see how it works, Download the zip file and unzip it. Open the 'paginato.html' in a browser.

When it is implemented in your webpage, 

1. Include the paginato.js and paginato.css in your html. 
2. Include jquery lib.
3. Add an empty div block. 
	<div class="paging-nav"></div>
4. Add the below script
    ```javascript
    var paginato = {
    'curPage':1,
    'totalPages':100,
    'pagevisble':15,
    'firstTxt':'|<<',
    'nextTxt':'>|',
    'prevTxt':'|<',
    'lastTxt':'>>|'
    };
    paginatoInit('paging-nav');
    
    function makeYourAjaxCall(currentPage) {
     /* Your code to do with the page content changes. currentPage has the current navigated page number */ 
    }
    ```


