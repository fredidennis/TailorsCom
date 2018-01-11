<?php 
 
 //Getting the page number which is to be displayed  
 $page = $_GET['page']; 

  //Importing the database connection 
 require_once('dbConnect.php');
 
 //Counting the total item available in the database 
 $total = mysqli_num_rows(mysqli_query($con, "SELECT id from feed"));

 //Calculating start for every given page number 
 $start = ($page - 1) * $total;

 //SQL query to fetch data of a range 
 $sql = "SELECT * from feed order by created_at DESC limit $start, $total";

  //Getting result 
 $result = mysqli_query($con,$sql);
 
 //Adding results to an array 
 
 $res = array(); 
 
 while($row = mysqli_fetch_array($result)){
	 array_push($res, array(
		 "likes"=>$row['_like'],
		 "dislikes"=>$row['_dislike'],
		 "name"=>$row['publisher'],
		 "publisher"=>$row['description'],
		 "image"=>$row['image_server_path'],
		 "cost"=>$row['cost'],
		 "email"=>$row['user_email'],
		 "image_id"=>$row['image_id'])
	 );
 }
 //Displaying the array in json format 
if(count($res) >=1){
 $rest ["result"] = $res;
 echo json_encode($rest);
} else {
$rest ["result"] = "no gallery feed";
 echo json_encode($rest);
}