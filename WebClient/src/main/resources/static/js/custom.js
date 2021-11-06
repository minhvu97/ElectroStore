$(document).ready(function() {
	"use strict";

	// 5. searchValue
	$('#searchValue').autocomplete({
		source: function(request, response) {
			$.ajax({
				type: 'GET',
				url: 'http://localhost:8082/api/products/autocomplete',
				data: {
					name: request.term
				},
			}).done(function(data) {
				console.log(data);
				response(data);
			});
		},
		minLength: 1,
		select: function(ui) {
			$('#searchValue').val(ui.item.value);
		}
	}).data('ui-autocomplete')._renderItem = function(ul, item) {
		var $li = $('<li>');
		let $a = $('<a>');
		var $img = $('<img class="ui-autocomplete-img">');
		$img.attr({
			src: "/product-photos/" + item.id + "/" + item.photo,
			alt: '123456'
		});
		$a.attr({
			class: "ui-autocomplete-row",
			href: "/single?code="+item.code
		});
		
		$li.attr('data-value', item.name);
		$li.append($a);
		$li.find('a').append($img).append(item.name);
		return $li.appendTo(ul);
	};
});


// category select option
$(document).ready(function(){
  // bind change event to select
  $('#agileinfo-nav_search').on('change', function () {
      var url = $(this).val(); // get selected value
      if (url) { // require a URL
          window.location = url; // redirect
      }
      return false;
  });
});

// Low to High