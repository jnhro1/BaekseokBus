<?php 
	header('Content-Type: text/html; charset=utf-8');
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $start=$_POST['startText'];
        $arrive=$_POST['arriveText'];
		$date=$_POST['dateText'];
		$clock=$_POST['clockText'];
		$userId=$_POST['userId'];




        if(empty($start)){
            $errMSG = "출발지를 입력하세요.";
        }
        else if(empty($arrive)){
            $errMSG = "도착지를 입력하세요.";
        }

        if(!isset($errMSG)) // 이름과 나라 모두 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다. 
                $stmt = $con->prepare('INSERT INTO RESERVATION(start, arrive, date, clock, userId) VALUES(:start, :arrive, :date, :clock, :userId)');
                $stmt->bindParam(':start', $start);
                $stmt->bindParam(':arrive', $arrive);
				$stmt->bindParam(':date', $date);
				$stmt->bindParam(':clock', $clock);
				$stmt->bindParam(':userId', $userId);

				

                if($stmt->execute())
                {
                    $successMSG = "예약이 등록되었습니다..";
                }
                else
                {
                    $errMSG = "예약에 실패하였습니다.";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                start: <input type = "text" name = "startText" />
				arrive: <input type = "text" name = "arriveText" />
                date: <input type = "text" name = "dateText" />
				clock: <input type = "text" name = "clockText" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>