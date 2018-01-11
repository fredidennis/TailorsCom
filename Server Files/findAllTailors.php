<?php 
 

 $page = $_GET['page']; 

 require_once('dbConnect.php');
 
$total = mysqli_num_rows(mysqli_query($con, "SELECT sno from users "));
 
 $start = ($page - 1) * $total;

 $sql = "SELECT * FROM users LIMIT $start,$total";

 $result = mysqli_query($con,$sql); 
 
 
 $res = array(); 
 
 while($row = mysqli_fetch_array($result)){
	 array_push($res, array(
		 "name"=>$row['name'],
		 "email"=>$row['email'],
		 "desc"=>$row['description'],
		 "gender"=>$row['gender'],
		 "speciality"=>$row['speciality'],
		 "location"=>$row['location'],
		 "contact"=>$row['contact'],
		 "imageUrl"=>$row['image_server_path'],
		 "created_at"=>$row['created_at'],
		 "starting_rate"=>$row['rate'],
		 "profile_id"=>$row['unique_id'],
		 "likes"=>$row['_like'],
		 "dislikes"=>$row['_dislike'])
	 );
 }
 $rest ["result"] = $res;
 echo json_encode($rest);