$(document).ready(function() {
	
	$(".minus").on("click", function(event) {
		event.preventDefault();
		
		productCode = $(this).attr("pid");
		inputQuantity = $("#quantity" + productCode);
		newQuantity = parseInt(inputQuantity.val()) - 1;
		if (newQuantity > 0) {
			inputQuantity.val(newQuantity);
			shopCartUpdateQuantity(productCode, newQuantity)	
		}
	});
	
	$(".plus").on("click", function(event) {
		event.preventDefault();
		
		productCode = $(this).attr("pid");
		inputQuantity = $("#quantity" + productCode);
		newQuantity = parseInt(inputQuantity.val()) + 1;
		
		inputQuantity.val(newQuantity);	
		shopCartUpdateQuantity(productCode, newQuantity)

	});
	
	function shopCartUpdateQuantity(productCode, quantity) {
		url = "/restapi/shopcart_update/" + productCode + "/" + quantity;
		
		$.ajax({
			type: "POST",
			url: url,
			beforeSend: function(xhr) {
				console.log(csrfHeaderName);
				console.log(csrfValue);
				xhr.setRequestHeader(csrfHeaderName, csrfValue);
			}
		}).done(function(response) {
			if (response !== 'Updated!') {
				alert(response);
			} else {
				updateCartItemSubtotal(productCode, quantity)
				updateCartTotal();
				updateCartTotalQuantity();
			}
		}).fail(function() {
			alert("update cart quantity error!!!");
		});
	}
	
	function updateCartItemSubtotal(productCode, quantity) {
		
		console.log(productCode);
		
		productPrice = $("#itemPrice" + productCode).text();
		
		console.log(productPrice);
		
		newSubTotal = parseFloat(productPrice) * quantity;
		
		$("#itemSubtotal" + productCode).text(newSubTotal + '');
	}
	
	function updateCartTotal() {
		total = 0.0;
		
		$(".cartline-total").each(function(index, element) {
			console.log("Hello index total" + index);
			total = total + parseFloat(element.innerHTML);
		});
		
		$("#cartInfoTotal").text(total + '');
	}
	
	function updateCartTotalQuantity() {
		total = 0;
		
		$(".qty").each(function(index, element) {
			console.log("Hello index quantity" + index);
			console.log("Hello element.innerHTML" + element.value);
			total = total + parseInt(element.value);
		});
		
		$("#cartQuantityTotal").text(total + '');
	}
	
	$(".remove-cartline").on("click", function(event) {
		event.preventDefault();
		
		url = $(this).attr("href");
		cartLineNumber = $(this).attr("cartLine");
		cartLineItem = $("#cartLine" + cartLineNumber);
		$.ajax({
			type: "POST",
			url: url,
			beforeSend: function(xhr) {
				console.log(csrfHeaderName);
				console.log(csrfValue);
				xhr.setRequestHeader(csrfHeaderName, csrfValue);
			}
		}).done(function(response) {
			if (response !== 'Removed!') {
				alert(response);
			} else {
				cartLineItem.remove();
				updateCartTotal();
				updateCartTotalQuantity();
			}
		}).fail(function() {
			alert("Delete cart error!!!");
		});
	});	
})







