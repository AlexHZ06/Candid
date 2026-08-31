import { useRef } from "react"
import { Link } from "react-router-dom"


function Index(){

    return(

        <div className="
        
            w-screen 
            min-h-screen 

        
        ">

            <div className="
            
                h-40 
                flex
                flex-row
                items-center
                pl-10
                pr-10
            
            ">

                <p className="

                    text-5xl
                    font-bold
                    flex-3
                    
                
                ">candid</p>
                <p className="
                
                    flex-5
                
                "></p>
                <Link to={"/signin"} className="
                
                    text-3xl
                    flex-1

                    hover:text-gray-500
                    active:text-black
                    
                    transition
                    duration-200
                
                ">Log In</Link>
                <Link to="/signup" className="
                
                    text-white
                    text-2xl
                    flex-1
                    bg-black
                    h-13
                    flex
                    justify-center
                    items-center
                    rounded-3xl

                    hover:bg-gray-900
                    hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)]

                    active:bg-white
                    active:text-black

                    transition
                    duration-100

                
                ">Sign Up</Link>

            </div>
            <div className="
            
                h-[75vh]
                
                overflow-hidden
                relative
            
            ">
                
                <div className="
                
                    absolute
                    flex
                    flex-col
                    pl-20
                    w-[100vh]

                    xl:w-[130vh]
                   
                    top-40

                
                ">
                    <p className="
                    
                        text-white
                        font-black
                        text-7xl

                        xl:text-8xl
                   

                    
                    ">Find the perfect Photographer for you</p>
                    <Link to="/signup" className="
                    
                        text-2xl
                       
                        bg-white
                        w-1/4
                        rounded-2xl
                        flex
                        justify-center
                        items-center
                        h-10
                        mt-10
                        tracking-widest

                        hover:shadow-[0_5px_5px_rgba(0,0,0,0.25)]
                        hover:bg-gray-900
                        hover:text-white

                        active:bg-white
                        active:text-black

                        transition
                        duration-200

                    ">Get Started</Link>
                </div>

                <img src="src\resources\ssscales (1).svg" className="

                    w-full
                    h-full 
                    object-cover


                "
                />

                

            </div>
        </div>  

    )
    
}

export default Index