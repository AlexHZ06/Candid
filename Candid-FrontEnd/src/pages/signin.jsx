import { Link } from "react-router-dom"
import { useRef, useState} from "react"
import { jwtDecode } from "jwt-decode"
import { useNavigate } from "react-router-dom"

function SignIn() {

    const userName = useRef("")
    const password = useRef("")
    const [response, setResponse] = useState("")
    const navigate = useNavigate()

    function submit(){

        fetch("/api/auth/public/login",{

            method:"POST",
            headers:{

                "Content-Type":"application/json"

            },
            body:JSON.stringify({

                "userName":userName.current.value,
                "password":password.current.value

            })

        }).then(response => response.json()).then(data =>{

            console.log(data)

            if(!data.sucsess){
                if(data.internalCode === 102){

                    setResponse("incorrect details")

                }
                else if(data.internalCode === 101){

                    setResponse("user does not exist")

                }
                else{

                    setResponse("internal error try again")

                }
            }
            else{

                localStorage.setItem("jwt", data.data.jwt)
                localStorage.setItem("refreshUUID", data.data.refreshUUID)
                const token = localStorage.getItem("jwt")
                const decoded = jwtDecode(token)

                if(decoded.role === "client"){

                    alert("clinet")

                }
                else{

                    navigate("/PHome")

                }

            }
            
            

        })

    }

    return (
        <div
            className="
                w-full
                h-screen
                overflow-hidden
                bg-cover
                bg-center
                bg-no-repeat
                flex
                items-center
                justify-center
            "
            style={{
                backgroundImage: "url('/src/resources/ssscales (1).svg')"
            }}
        >

            <div className="
            
                flex
                flex-col
                bg-white
                rounded-md
                w-[40vh]
                h-[50vh]
                shadow-[0_0_10px_rgba(0,0,0,0.25)]
                items-center
            
            ">
                <p className="
                
                    text-4xl
                    font-bold
                    tracking-widest
                    pt-8
                
                ">Welcome</p>
                <p className="
                
                    text-2xl
                    tracking-wide
                    pt-8
                
                ">Enter Your Details</p>
                <input ref={userName} placeholder="UserName" type="text" className="
                
                    border-2
                    mt-10
                    h-10
                    rounded-md
                    w-[30vh]
                    pl-2
                    border-neutral-500
                    hover:border-black

                    transition
                    duration-100

                "/>
                <input ref={password} placeholder="Password" type="password" className="
                
                    border-2
                    mt-10
                    h-10
                    rounded-md
                    w-[30vh]
                    pl-2
                    border-neutral-500
                    
                    hover:border-black

                    transition
                    duration-100

                
                "/>
                <p className="
                
                    text-red-600
                
                ">{response}</p>
                <Link className="
                
                    text-gray-500
                    pt-3

                    hover:text-gray-700
                    active:text-black

                    transition
                    duration-100
                
                ">Forgot Password</Link>
                <button onClick={submit} className="
            
                    bg-black
                    w-[30vh]
                    h-10
                    rounded-md
                    text-white

                    hover:bg-gray-900
                    mt-5

                    transition
                    duration-100

                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                    active:bg-white
                    active:text-black

                    

                
                ">Submit</button>
            </div>

        </div>
    )
}

export default SignIn