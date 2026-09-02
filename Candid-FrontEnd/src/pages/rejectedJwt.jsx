
import { useNavigate } from "react-router-dom"

function RejectedJwt(){

    const navigate = useNavigate()

    function backToHome(){

        localStorage.clear()
        navigate("/")
        
    }

    return(

        <div className="
        
            w-screen 
            min-h-screen 
            flex
            flex-col
            items-center
        
        ">
            <div className={`
            
                w-full
                flex
                flex-row
                shadow-[0_0_10px_rgba(0,0,0,0.25)]
                pl-4
                pr-4
                h-15
                items-center
            
            `}>

                <p className="
                
                    flex-7
                    font-black
                    text-3xl
                    text-amber-500
                    tracking-widest
                    
                
                ">Candid</p>
                <p className="
                
                    flex-6

                "></p>
            </div>            
            <div className="
            
                flex
                border
                border-neutral-300
                w-5/10
                h-[25vh]
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                flex-col
                items-center
                rounded-md
                mt-25
            
            ">
                <p className="
                
                    text-red-600
                    text-2xl
                    w-[80vh]
                    text-center
                    pt-10
                
                ">Your JWT token has been rejected, this is due to it being tampered with, or your Refresh JWT expiring causing you to sign out </p>
                    <button onClick={backToHome} className="
                
                        bg-black
                        w-[20vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-10

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        

                    
                    ">Back to home page</button>
            </div>
            
        </div>

    )

}

export default RejectedJwt