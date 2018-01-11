<?php 
 

 $page = $_GET['page']; 
 $user = $_GET['user'];

 require_once('dbConnect.php');
 
$total = mysqli_num_rows(mysqli_query($con, "SELECT id from feed "));
 
 $start = ($page - 1) * $total;

 $sql = "SELECT * FROM feed WHERE publisher='$user' LIMIT $start,$total";

 $result = mysqli_query($con,$sql); 
 
 
 $res = array(); 
 
 while($row = mysqli_fetch_array($result)){
	 array_push($res, array(
		 "desc"=>$row['description'],
		 "imageUrl"=>$row['image_server_path'],
		 "created_at"=>$row['created_at'],
		 "starting_rate"=>$row['cost'],
		 "profile_id"=>$row['unique_image_id'],
		 "likes"=>$row['_like'],
		 "dislikes"=>$row['_dislike'])
	 );
 }
 $rest ["result"] = $res;
 echo json_encode($rest);