import CHeader from "../compnents/cheader"
import { useState, useEffect } from "react"
import { jwtService } from "../logic/jwt"
import { useParams } from "react-router-dom"
import { useLocation } from "react-router-dom"
import { useNavigate } from "react-router-dom"

function FormFill(){

    const {photographerid} = useParams();
    const [questions, setQuestions] = useState([])
    const location = useLocation()
    const navigate = useNavigate()
    const [errorText, setErrorText] = useState("")
    
    const bookingId = location.state?.slotid

    const responses = {}

    const jwt = new jwtService()

    function loadQuestions(){

        fetch("/api/form/getquestions", {

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                photographerId:photographerid

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

                                    photographerId:photographerid

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

    function submit(){

        if(Object.keys(responses).length !== questions.length){

            setErrorText("must answer all questions")
            return

        }
        
        let empty = false
        for(let key in responses){

            if(responses[key].trim() === ""){

                empty = true

            }

        }
        if(empty){

            setErrorText("must answer all questions")
            return

        }

        const response = []

        for(let key in responses){

            response.push({

                responseid:-1,
                response:responses[key],
                questionid:key,
                bookingid:bookingId

            })

        }

        console.log(response)


        fetch("/api/form/client/addresponses", {

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify(response)

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/form/client/addresponses", {

                            method:"POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body:JSON.stringify(response)

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                console.log("added")
                                navigate("/temp")

                            }else{

                                console.log("error")

                            }

                        })

                    }

                }else{

                    console.log("error")

                }

            }
            else{

                console.log("added")
                navigate("/temp")

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
            <CHeader active=""/>
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
                            mb-3
                            
                        ">
                                        
                            <p className="
                                            
                                text-[2.2vh]
                                flex
                                pl-5
                                pt-5
                                w-full
                                
                                                

                                            
                            ">{question.question}</p>
                            <textarea onChange={(e) => {responses[question.questionid] = e.target.value}} placeholder="Type Response here" className="
                            
                                p-4
                                w-9/10
                                h-full
                                mb-10
                                mt-8
                                flex
                                focus:outline-
                                resize-none
                                outline
                                outline-neutral-200
                                rounded-xl

                            "></textarea>
                        </div>

                    ))

                )}
                

            </div>
            
            <p className="
            
                text-red-600
                mb-4
            
            ">{errorText}</p>
            
            <div className="
            
                flex
                gap-10
            
            ">
                
                <button onClick={submit} className="
                            
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
                            
                ">Submit</button>
            </div>
        </div>
    )

}

export default FormFill