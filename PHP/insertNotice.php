<?php 
	header('Content-Type: text/html; charset=utf-8');
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $title=$_POST['titleText'];
        $content=$_POST['contentText'];
		$date=$_POST['dateText'];
		$writer=$_POST['writerText'];




        if(empty($title)){
            $errMSG = "제목을 입력하세요.";
        }
        else if(empty($content)){
            $errMSG = "내용를 입력하세요.";
        }

        if(!isset($errMSG)) // 이름과 나라 모두 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다. 
                $stmt = $con->prepare('INSERT INTO NOTICE(title, content, date, writer) VALUES(:title, :content, :date, :writer)');
                $stmt->bindParam(':title', $title);
                $stmt->bindParam(':content', $content);
				$stmt->bindParam(':date', $date);
				$stmt->bindParam(':writer', $writer);

				

                if($stmt->execute())
                {
                    $successMSG = "게시글이 등록되었습니다..";
                }
                else
                {
                    $errMSG = "게시글 등록에 실패하였습니다.";
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
                title: <input type = "text" name = "titleText" />
				content: <input type = "text" name = "contentText" />
                date: <input type = "text" name = "dateText" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>