if (Function.prototype.name === undefined && Object.defineProperty !== undefined) {
    Object.defineProperty(Function.prototype, 'name', {
        get: function() {
            var funcNameRegex = /function\s([^(]{1,})\(/;
            var results = (funcNameRegex).exec((this).toString());
            return (results && results.length > 1) ? results[1].trim() : "";
        },
        set: function(value) {}
    });
}
else if ( Object.defineProperty == undefined ){
	alert('Seu browser nao tem suporte para esse sistema. Tente usar Google Chrome ou Mozilla FireFox!');
}