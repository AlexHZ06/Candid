import { Link } from "react-router-dom"

function SignUp() {
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
                <div className="
                
                    flex
                    items-center
                    flex-col
                    hidden
                
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
                    <input placeholder="UserName" type="text" className="
                    
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
                    <div className="
                    
                        flex
                        items-center
                        flex-col
                    
                    ">
                        <input placeholder="Password" type="password" className="
                        
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
                        <input placeholder="Confirm Password" type="password" className="
                        
                            border-2
                            mt-2
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
                        
                        ">Error message</p>
                    </div>
                    <button className="
                
                        bg-black
                        w-[30vh]
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

                        

                    
                    ">Submit</button>
                </div>
                <div className="
                
                    flex 
                    flex-col
                    items-center
                    hidden
                
                ">
                    <p className="
                    
                        text-2xl
                        tracking-wide
                        pt-8
                    
                    ">Enter Your Details</p>
                    <input placeholder="Enter Email" type="email" className="
                            
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
                    
                    ">Error Message</p>

                    <button  className="
                
                        bg-black
                        w-[30vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-5

                        transition
                        duration-100

                        disabled:bg-neutral-500
                        disabled:active:text-white

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                    ">Submit</button>

                    <div className="
                    
                        flex
                        flex-col
                        items-center
                        pt-5
                        

                    ">
                        <p>Enter the code sent to your email</p>
                        <input placeholder="X X X X X" type="text" className="
                                
                            border-2
                            mt-3
                            h-10
                            rounded-md
                            w-[30vh]
                               
                            border-neutral-500
                                        
                            hover:border-black

                            transition
                            duration-100
                            text-center
                        "/>  
                        <button className="
                        
                            text-neutral-500
                            pt-2

                            hover:text-neutral-600
                            active:text-black

                            disabled:hover:text-neutral-500
                            disabled:active:text-neutral-500
                        
                        ">
                        Resend Email
                        </button>
                        <p className="
                        
                            text-red-600
                        
                        ">error message</p>
                    <button className="
                
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

                    ">continue</button>

                    </div>
                </div>
                <div>

                    <p className="
                    
                        text-2xl
                        tracking-wide
                        pt-8
                    
                    ">What are you looking for ?</p>

                </div>
            </div>


        </div>
    )
}

export default SignUp