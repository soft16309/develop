Array.prototype.contain = function(val) {
	for(var i = 0; i < this.length; i++) {
		if(this[i] == val) {
			return true;
		}
	}
	return false;
};

function loadImg(jq, file) {
	var reader = new FileReader();
	var imgFile;
	reader.onload = function(e) {
		imgFile = e.target.result;
		jq.attr('src', imgFile);
	};
	reader.readAsDataURL(file);
}