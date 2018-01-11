<?php
if(isset($_POST["name"]) && isset($_POST["image"])){
	$name = $_POST["name"];
	$image = $_POST["image"];
	
	$decodedImage = base64_decode("$image");
	if (file_put_contents("upload/".$name.".JPG", $decodedImage) != false){
		echo "uploaded_success";
				exit;
	}
	else{
		echo "uploaded_failed";
		exit;
	}
}
else{
	echo "image_not_in";
	exit;
}

?>