<?php

	if($_SERVER['REQUEST_METHOD']=='GET'){
		$id = $_GET['id'];
		$sql = "SELECT * FROM feed WHERE unique_image_id = '$id'";
		require_once('dbConnect.php');
		
		$r = mysqli_query($con,$sql);
		
		$result = mysqli_fetch_array($r);
		
		header('content-type: image/jpeg');
	
		echo base64_decode($result['actual_image']);
		
		mysqli_close($con);
		
	}else{
		echo "Error";
	}