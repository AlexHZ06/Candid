import { Link } from "react-router-dom";
import PHeader from "../compnents/pheader";
import { useEffect, useState } from "react";
import { jwtService } from "../logic/jwt";

function FormEdit(){

    const [questions, setQuestions] = useState([])

    const jwt = new jwtService()

    function loadQuestions(){

        fetch("/api/form/getquestions", {

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                photographerId:jwt.decodeUserId()

            })

            }).then(response => response.json()).then(async data =>{

                if(!data.success){

                    if(data.internalCode === 401){

                        let result = await jwt.decodeUserId();
                        if(result){

                            fetch("/api/form/getquestions", {

                                method:"POST",
                                headers: {

                                    "Content-Type": "application/json",
                                    auth: localStorage.getItem("jwt")

                                },
                                body:JSON.stringify({

                                    photographerId:jwt.decodeUserId()

                                })

                            }).then(res => res.json()).then(d => {

                                if(!data.success){

                                    console.log("error")
                                    setQuestions([])

                                }
                                else{

                                    console.log(d.data)
                                    setQuestions(d.data)

                                }

                            })

                        }

                    }
                    else{

                        console.log("error")
                        setQuestions([])

                    }

                }
                else{

                    console.log(data.data)
                    setQuestions(data.data)

                }
                    
        
            })

    }        

    

    useEffect(()=>{

        loadQuestions()

    },[])

    function deleteQuestion(id){

        fetch("/api/form/photographer/markquestionsasinactive", {

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                questionId:id

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/form/photographer/markquestionsasinactive", {

                            method:"POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                questionId:id

                            })

                        }).then(res => res.json()).then(d => {

                            if(!d.success){

                                console.log("error")

                            }else{

                                console.log("all good")
                                loadQuestions()

                            }

                        })                     

                    }


                }else{

                    console.log("error")

                }

            }else{

                console.log("all good")
                loadQuestions()

            }

        })

    }

    return(
        <div className="
        
            w-screen 
            min-h-screen
            flex
            flex-col
            items-center 
        
        "> 
            <PHeader active="Profile"/>
            <div className="
            
                p-3
                w-[150vh]
                h-[75vh]
                mt-15
                overflow-y-scroll
                [&::-webkit-scrollbar]:hidden
                [-ms-overflow-style:none]
                scrollbar-width:none
               

            ">
                {questions.length === 0 ? (

                    <div className="
                    
                        flex
                        w-full
                        h-[30vh]
                        flex-col
                        items-center
                        mb-10
                        justify-center                        
                    
                    ">
                        <p className="
                        
                            text-2xl
                            tracking-wider
                        
                        ">No questions make some</p>
                    </div>

                ):(

                    questions.map(question =>(
                                        
                        <div key={question.questionid} className="
                            relative
                            flex
                            border
                            border-neutral-300
                            w-full
                            h-[30vh]
                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                            flex-col
                            items-center
                            rounded-md
                            mb-10
                            justify-center
                            
                        ">
                                        
                            <p className="
                                            
                                text-[2.5vh]
                                flex
                                items-center
                                justify-center     
                                w-8/10
                                                

                                            
                            ">{question.question}</p>
                                <div className="
                                    absolute
                                    bottom-4
                                    right-5
                                ">
                                <button onClick={()=>{deleteQuestion(question.questionid)}} className="
                                                
                                    bg-red-500
                                    rounded-xl
                                    h-8
                                    w-[10vh]
                                    text-white
                                    font-semibold
                                    mt-10
                                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
                                                    
                                    ">
                                    Remove
                                </button>
                            </div>
                        </div>

                    ))

                )}
                

            </div>
            
            
            <div className="
            
                flex
                gap-10
            
            ">
                <button className="
                            
                    bg-black
                    text-white
                    rounded-xl
                    w-[10vh]
                    h-8.75
                    flex
                    items-center
                    justify-center
                    hover:bg-gray-900
        
                    transition
                    duration-100

                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                    active:bg-white
                    active:text-black

                    xl:h-[5vh]
                    xl:w-[15vh]
                            
                ">Save changes</button>
                <Link to={"/makeQuestion"} className="
                            
                    bg-black
                    text-white
                    rounded-xl
                    w-[10vh]
                    h-8.75
                    flex
                    items-center
                    justify-center
                    hover:bg-gray-900
        
                    transition
                    duration-100

                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                    active:bg-white
                    active:text-black

                    xl:h-[5vh]
                    xl:w-[15vh]
                            
                ">Add question</Link>
            </div>
        </div>
    )

}

export default FormEdit