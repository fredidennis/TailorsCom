<?php
 define('HOST','127.0.0.1');
 define('USER','root');
 define('PASS','');
 define('DB','tailorscom_db');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
