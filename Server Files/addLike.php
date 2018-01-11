<?php 
$page = $_GET ['page']; 

require_once('dbConnect.php');

$sql = "UPDATE feed SET _like = _like + 1 WHERE image_id =$page";
$query = mysqli_query($con,$sql);
//$query -> (array(':page' => $page));

 if ($query) {   
 	echo "success";       
    return true;
} else {
	echo "failed";
  	return false;
}
