import PHeader from "../compnents/pheader"
import { Link } from "react-router-dom"

function Booking(){

    return(

        <div className="
        
            w-screen 
            min-h-screen 
        
        ">
            <PHeader active={"Clients"}/>
            <div className="
            
                mt-5
                h-[90vh]
                flex
                flex-col
                overflow-y-scroll
                items-center
                [&::-webkit-scrollbar]:hidden
                [-ms-overflow-style:none]
                scrollbar-width:none
            
            ">
                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-6/10
                    h-100
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-10
                    justify-self-center
                    shrink-0
                
                ">
                    <div className="
                    
                        flex
                        w-full
                        justify-between
                        mt-5
                    
                    ">
                        <div className="
                        
                            flex
                        
                        ">
                            <img className="
                            
                                bg-blue-300
                                rounded-full
                                ml-7
                                w-[10vh]

                            " src="src\resources\user.png" /> 
                            <div className="
                            
                                ml-5
                                mt-2
                            
                            ">
                                <p className="
                                
                                    font-bold
                                    
                                
                                ">Name placeholder</p>
                                <p className="
                                
                                    text-neutral-600
                                
                                ">user tag</p>
                            </div>
                            
                        </div>
                        <p className="
                        
                            bg-amber-300
                            h-7
                            w-[10vh]
                            flex
                            items-center
                            justify-center
                            rounded-xl
                            mt-2
                            mr-7
                        
                        ">pending</p>
                    </div>
                    <div className="
                    
        
                        w-full
                        h-full
                        flex
                        flex-col
                        p-5
                    
                    ">
                        <div className="
                        
                        
                            w-8/10
                            flex-1
                            flex
                            gap-7
                            p-2
                        
                        ">
                            <div className="
                            
                                flex
                                flex-col
                                gap-4
                            
                            ">
                                <div className="
                                
                                    flex
                                    items-center
                                    gap-2
                                
                                ">
                                    <img className="
                                
                                        w-[2vh]
                                        h-[2vh]

                                    " src="src\resources\calendar (3).png" />                               
                                    <p>11-06-2026</p>
                                </div>
                                <div className="
                                
                                    flex
                                    items-center
                                    gap-2
                                
                                ">
                                    <img className="
                                
                                        w-[2vh]
                                        h-[2vh]

                                    " src="src\resources\location.png" />
                                    <p>Greenwhich 1.1mil</p>
                                </div>
                            </div>
                            <div className="
                            
                                flex
                                flex-col
                                gap-4
                            
                            ">
                                <div className="
                                
                                    flex
                                    items-center
                                    gap-2
                                
                                ">
                                    <img className="
                                
                                        w-[2vh]
                                        h-[2vh]

                                    " src="src\resources\clock.png" />
                                    <p>10:30 11:30 (1 hour)</p>
                                </div>
                                <div className="
                                
                                    flex
                                    items-center
                                    gap-2
                                
                                ">
                                    <img className="
                                
                                        w-[2vh]
                                        h-[2vh]

                                    " src="src\resources\image.png  " />
                                    <p>wedding</p>
                                </div>
                            </div>
                        </div>
                        <div className="
                        
                            rounded-2xl
                            w-ful
                            flex-1
                            bg-neutral-200
                            p-3
                            flex

                        ">
                            <img className="
                                
                                w-[3vh]
                                h-[3vh]
                                object-cover
                                opacity-50
                                

                            " src="src\resources\quote.png" />
                            <p className="
                            
                               
                                w-full
                                pr-20
                                ml-10
                                flex
                                items-center
                                tracking-wider
                                font-semibold
                                text-[2vh]
                                text-neutral-800
                            
                            ">Hi mark, was wondering if you would be free to do some wedding photogrpahy for my upcomming wedding</p>
                        </div>
                    </div>
                </div>
                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-6/10
                    h-50
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-5
                    justify-self-center
                    shrink-0
                
                ">
                    <div className="
                    
                        flex
                        w-full
                        justify-between
                    
                    ">
                        <p className="
                        
                            font-semibold
                           
                            pl-5
                            pt-3
                            text-[2vh]
                            
                        
                        ">Clients Details</p>
                        <Link className="
                        
                            pr-5
                            pt-3
                            text-neutral-600
                            hover:text-black
                            active:text-neutral-600
                        
                        ">See profile</Link>
                    </div>
                    <div className="
                    
                        flex
                        w-full
                        justify-between
                        
                        p-5
                    
                    ">
                        <div className="
                        
                            flex
                            flex-col
                            gap-4
                            
                            
            
                        
                        ">
                            <div className="
                                    
                                        flex
                                        items-center
                                        gap-2
                                    
                                    ">
                                <img className="
                                    
                                    w-[3vh]
                                    h-[3vh]
                                    

                                " src="src\resources\user (1).png" />
                                <p>Alexander Hernandez</p>
                            </div>
                            <div className="
                                    
                                        flex
                                        items-center
                                        gap-2
                                    
                                    ">
                                <img className="
                                    
                                    w-[3vh]
                                    h-[3vh]
                                    

                                " src="src\resources\phone-svgrepo-com.svg" />
                                <p>+44 74165691</p>
                            </div>
                            <div className="
                                    
                                        flex
                                        items-center
                                        gap-2
                                    
                                    ">
                                <img className="
                                    
                                    w-[3vh]
                                    h-[3vh]
                                    

                                " src="src\resources\email.png" />
                                <p>Alexander@email.com</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-6/10
                    h-100
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-5
                    justify-self-center
                    shrink-0
                
                ">
                    <p className="
                   
                        w-full
                        font-semibold   
                        pl-5
                        pt-3
                        text-[2vh]
                            
                    ">Booking Form</p>
                </div>
            </div>

        </div>

    )

}

export default Booking