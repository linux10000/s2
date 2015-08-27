var contador_aba = 1;

function inicializaTabs(div){
	$( div ).append('<ul></ul>');
	
	var tabs = $( div ).tabs();	
	
	tabs.delegate( "span.ui-icon-close", "click", function() {
	      var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
	      $( "#" + panelId ).remove();
	      tabs.tabs( "refresh" );
	});
}

function inicializaFormAjax(divForm, form, url, instanciarAutoCompletes, paginaDestino, callback){
	
	if ( instanciarAutoCompletes != null )
		instanciarAutoCompletes.call();
	
	$('body :input:not([readonly]):enabled:visible:first').focus();
	
//	$('body').on('keydown', 'input, select, textarea', function(e) {
//		var self = $(this)
//		  , form = self.parents('form:eq(0)')
//		  , focusable
//		  , next
//		  , prev
//		  ;
//
//		if (e.shiftKey) {
//		 if (e.keyCode == 13) {
//		     focusable =   form.find('input,a,select,button,textarea').filter(':visible:not([readonly])');
//		     prev = focusable.eq(focusable.index(this)-1); 
//
//		     if (prev.length) {
//		        prev.focus();
//		     } else {
//		        form.submit();
//		    }
//		  }
//		}
//		  else
//		if (e.keyCode == 13) {
//		    focusable = form.find('input,a,select,button,textarea').filter(':visible:not([readonly])');
//		    next = focusable.eq(focusable.index(this)+1);
//		    if (next.length) {
//		        next.focus();
//		    } else {
//		        form.submit();
//		    }
//		    return false;
//		}
//	});

	$( form ).submit(function( event ) {
		event.preventDefault();				
		bloqueiaDiv($(divForm));
		
		var validacao_regras = [];
		
		$("input[id^='regra_']", $(this) ).each(function(){
			validacao_regras.push( $(this).val() );
		});
		
		validacao_regras = "&validacao_regras="+validacao_regras;
			  
		$.ajax({
			  url: url,
			  type: "POST",
			  data: $(this).serialize() + validacao_regras,
			  async : true
			}).success(function(data, textStatus, jqXhr){
				var source = $('<div>' + data + '</div>');
				$(form).html( source.find(divForm) );
				
				if ( instanciarAutoCompletes != null )
					instanciarAutoCompletes.call();
				
				if ( callback != null)
					callback.call();
//				inicializaAutoComplete('#combo_pessoa', 
//						'#combo_pessoa_id', 
//						'http://localhost:8080/abserp2/sistema/restrito/fabricante/manutencao/listar_combo_pessoa',
//						'pesnid',
//						'pescchave',
//						null
//						);
				//$(form).html(source.find(divForm));
				//document.location.reload(); //isso funciona sem mensagens de erro
				
				//$("#divForm").html($(source, "#divForm").html());
				
				//$("#divForm").html( source.find(divForm) );
				//$(this).html(data);
			}).done(function() {
			 	 desbloqueiaDiv($(divForm));
			 	$('body :input:not([readonly]):enabled:visible:first').focus();
			}).error(function(jqXhr, textStatus, exception){
				
				if ( jqXhr.status == 303 ){
					if ( paginaDestino != null ){
						top.location=paginaDestino;
					}
					else {
							alert('ERRO:Foi informado no controller que após o POST do formulario occorer com sucesso deve-se redirecionar para outra pagina. Entretanto, essa pagina nao foi informada na chamada da funcao do javascript. Mesmo assim, o commit ocorreu!!!!!');
					}
				}
				else {
					//alert(exception);
					alert(jqXhr.responseText);
				}
				
				desbloqueiaDiv($(divForm));
			});
			 
	});
};

function inicializaAutoComplete(inputText, inputTextId, url, campoId, campoDescricao, idPai) {
	  $( inputText ).autocomplete({
        source:  function( request, response ) {
            $.ajax({
                url: url,
                dataType: "json",
                type: "POST",
                data: {
                  featureClass: "P",
                  style: "full",
                  maxRows: 12,
                  name_startsWith: request.term,
                  id_pai : idPai
                },
                error: function() {
                    alert($.makeArray(arguments));
                },
                success: function( data ) {
                    response( $.map( data, function( item ) {
                        return {                        	  
                            label: eval('item.'+campoDescricao) ,
                            value: eval('item.'+campoId),
                          };                            
                      }));
                    }
            });
	      },
	      search: function () {
	    	  
	           $(inputTextId).val(null);
	           
	          if (this.value.length < 1) {	        	  
	              return false;
	          }
	          
	      },
	      focus: function () {
	          // prevent value inserted on focus
	          return false;
	      },
	      select: function (event, ui) {

	          $(inputTextId).val(ui.item.value);
	          this.value = ui.item.label;
	          return false;
	      },
	      open: function() {
	          $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
	        },
	        close: function() {
	          $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
	        }
	  }); 
};
    	
function carregaPagina(url, divRecebedor){
	bloqueiaDiv(divRecebedor);
	
//	setTimeout(function(){
			$.ajax({
				  url: url,
				  type: "POST",
				  async : true,
				  dataType : "html"
				}).success(function(data, textStatus, jqXhr){

					divRecebedor.html(data);
					
				}).done(function() {
				 	 desbloqueiaDiv(divRecebedor);
				 	$('body :input:not([readonly]):enabled:visible:first').focus();
				}).error(function(jqXhr, textStatus, exception){
					//alert(exception);
					alert(jqXhr.responseText);
				});
//	}, 200);
}

function bloqueiaDiv(div){
    div.block({ 
      //message: $('#displayBox'),
      message: '<div id=\"displayBox\"><img alt=\"\" src=\"'+window.parent.obtemContextPath()+'/recursos/imagens/ajax-loader.gif\"><h3>Aguarde...</h3></div>',
   	  //message: '<div id=\"displayBox\"><img alt=\"\" src=\"/abserp2/recursos/imagens/ajax-loader.gif\"><h3>Aguarde...</h3></div>',
   	  
      css: { 
          top:  ($(window).height() - 400) /2 + 'px', 
          left: ($(window).width() - 400) /2 + 'px', 
          width: '400px' 
      } 
    });  
}

function desbloqueiaDiv(div){
	div.unblock();		
}

function excluirRegistro(url, oTable, sDiv){
			
	try{

		var r = window.confirm("Deseja realmente excluir?");
					
		if ( r == true ){
			bloqueiaDiv($(sDiv));
			
			$.ajax({
		        type: "GET",
		        url: url,
		        cache: false,
		        async: true,
		        
		    }).done(function(result){
		    		desbloqueiaDiv($(sDiv));
		    		$(oTable).dataTable().fnDraw();
		   		 });
		
	}
	} catch (ex){
		alert(ex.message);
	}
	
}

function getURL(url, params){
    return $.ajax({
        type: "POST",
        url: url,
        cache: false,
        async: false,
        data : params
    }).responseText;
};

function chamaURLPostSemRetorno(url, params, fnCallbackSucesso, fnCallbackErro){
	$.ajax({
		  url: url,
		  type: "POST",
		  async: true,
		  data: params
		}).success(function(data, textStatus, jqXhr){
			if ( fnCallbackSucesso != null )
				fnCallbackSucesso.call();
		}).error(function(jqXhr, textStatus, exception){
			if ( fnCallbackErro != null )
				fnCallbackErro.call();
			
			alert(jqXhr.responseText);
		});
}

function abrirPopup(div, titulo, url, largura, altura, modal, callbackQuandoFecha, params){
//	$( div ).append('<iframe width="'+largura+'" height="'+altura+'" frameBorder="0" src=""/>');
//	
//	$( div ).dialog({
//	      autoOpen: false,
//	      height: 'auto',
//	      width: 'auto',
//	      modal: modal,
//	      close: function(){
//	    	  if ( callbackQuandoFecha != null)
//	    		  callbackQuandoFecha.call(); 
//	      }
//	});
	$('body').append('<div id="'+div+'" title="'+titulo+'"></div>');
	$('#'+div).append('<div id="'+div+'_janela_conteudo"></div>');
	
	$('#'+ div).dialog({
	      autoOpen: true,
	      height: largura,
	      width: altura,
	      modal: modal,
	      open: function( event, ui ) {

	  		bloqueiaDiv($('#'+div+'_janela_conteudo'));
			
//	  		var aux_url = '';
//	  		if ( $('#'+div+'_janela_conteudo_caminho_substituto').val() != '' )
//	  			aux_url = $('#'+div+'_janela_conteudo_caminho_substituto').val();
//	  		else
//	  			aux_url = url;
	  		
			setTimeout(function(){
			$.ajax({
				  url: url,
				  type: "POST",
				  async : true,
				  dataType : "html",
				  data: params
				}).success(function(data, textStatus, jqXhr){					
					$('#'+div+'_janela_conteudo').html(data);
				}).done(function() {
				 	 desbloqueiaDiv($('#'+div+'_janela_conteudo'));

				}).error(function(jqXhr, textStatus, exception){
					alert(jqXhr.responseText);
					desbloqueiaDiv($('#'+div+'_janela_conteudo'));
				});}, 200);
	      },
	      close: function(){
			if ( callbackQuandoFecha != null )
				callbackQuandoFecha.call();
			
	    	  $('div[aria-describedby="'+div+'"]').remove();
	    	  $('#'+div).remove();				
	      }
	});
}

//function abrirPopup(div, urlSubstituta){
//	 //$( div + ' iframe' ).attr('src',url);
//	
//	 $('#'+div+'_janela_conteudo_caminho_substituto').val(urlSubstituta);
//	
//	 $('#' + div ).dialog( 'open' );
//}

function fecharPopup(div){
	 //$( div + ' iframe' ).attr('src',url);
	 
	 $('#' + div ).dialog( 'close' );
}

function converteStringToFloat(valor){
	var aux = String(valor).replace(".", "").replace(",", ".");
	
	if ( aux == '')
		aux = 0;
	
	return parseFloat(aux);
}

function converteStringToInt(valor){
	var aux = valor;
	
	if ( aux == '')
		aux = 0;
	
	return parseInt(aux);
}

function converteFloatToString(valor){
	var aux = parseFloat(valor);		
	
	if ( aux == '')
		aux = '0';
	else
		aux = String(valor).replace(".", ",");
	
	return aux;
}

function converteIntToString(valor){
	var aux = String(valor);
	
	if ( aux == '')
		aux = '0';
	
	return parseInt(aux);
}

function arredondar(valor, casas){
	return +(Math.round(valor + "e+" + casas)  + "e-" + casas);
}

function inputAceitaSomenteNumeroEvirgula(idInput){
	$("#"+idInput).keypress( function(e) {
		var numberPattern = /[0-9,]+/g;
	    var chr = String.fromCharCode(e.which);
	    var qtdeVirgulas = $("#"+e.target.id).val().match(/,/g);
	    if ( chr.match(numberPattern) == null || ( chr == ',' && qtdeVirgulas != null && qtdeVirgulas.length > 0) )
	    	return false;
	});
}