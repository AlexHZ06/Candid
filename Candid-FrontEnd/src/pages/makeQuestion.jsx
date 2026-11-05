import { Link } from "react-router-dom";
import PHeader from "../compnents/pheader";
import { jwtService } from "../logic/jwt";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";

function MakeQuestion() {

    const questionText = useRef();

    const navigate = useNavigate()

    const jwt = new jwtService()

    function saveQuestion(){

        console.log(questionText.current.value)

        if(questionText.current.value === ""){

            return

        }

        fetch("/api/form/photographer/addquestions", {

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                questionid:-1,
                photographerid:jwt.decodeUserId(),
                question:questionText.current.value,
                active:true

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await jwt.requestJwt()
                    if(result === true){

                        fetch("/api/form/photographer/addquestions", {

                            method:"POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                questionid:-1,
                                photographerid:jwt.decodeUserId(),
                                question:questionText.current.value,
                                active:true

                            })

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                console.log(d.data)
                                navigate("/formedit")
                                

                            }
                            else{

                                console.log("error")

                            }

                        })

                    }

                }
                else{

                    console.log("error")

                }

            }
            else{

                console.log(data.data)
                navigate("/formedit")

            }

        })

    }

    return (
        <div className="
        
            w-screen 
            min-h-screen
            flex
            flex-col
            items-center 
        
        ">
            <PHeader active="Profile" />

            <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-[150vh]
                    h-[30vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mb-10
                    mt-20
                
                ">
                <div className="
                    
                        w-9/10
                        flex
                        justify-between
                        items-center
                        
                    
                    ">
                    <p className="
                        
                        text-2xl
                        font-semibold
                        text-neutral-600
                            
                        pt-5
                        
                    ">Question</p>
                </div>
                <textarea ref={questionText} placeholder="Type Question here" maxLength={225} className="
                    
                    text-[2.5vh]
                    mt-8
                    w-8/10
                    h-1/2
                    
                    hover:outline-0
                    focus:outline-0
                    resize-none
                    
                "></textarea>
            </div>


            <div className="
            
                flex
                gap-10
            
            ">
                <button onClick={saveQuestion} className="
                            
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
                            
                ">Add question</button>
                <Link to={"/formedit"} className="
                            
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
                            
                ">Cancel</Link>
            </div>
        </div>
    )

}

export default MakeQuestion