import CHeader from "../compnents/cheader"
import user from "../resources/user.png"
import send from "../resources/send_16682202.png"

function PchatPage(){

    return(

        <div className="
        
            w-screen 
            min-h-screen 
        
        ">
            <CHeader active={"Bookings"}/>
            <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-7/10
                    h-[80vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-10
                    justify-self-center
                

            ">
                <div className="
                
                    border-b-2
                    w-full
                    border-neutral-200
                    h-35
                    flex
                    items-center
                
                ">
                    <img className="

                        bg-blue-300
                        rounded-full
                        ml-7
                        h-15
                        

                    " src={user} />
                    <p className="
                    
                        text-[2vh]
                        ml-5
                        font-semibold
                        tracking-wider
                    
                    ">PlaceHolderName</p>    
                    <div className="
                    
                        flex-1
                        items-start
                        h-full
                        justify-end
                        flex
                    
                    ">
                        <button className="
                     
                            mr-2
                            mt-2
                            rounded-2xl
                            w-15
                            h-7
                            flex
                            items-center
                            justify-center
                            border
                            border-neutral-200

                            hover:border-neutral-400
                            active:border-neutral-200
                        
                        ">Back</button>
                    </div>         
                </div>
                <div className="
                
                    w-full
                    h-full
                    p-5
                    overflow-y-scroll
                    flex
                    flex-col
                    [&::-webkit-scrollbar]:hidden
                    [-ms-overflow-style:none]
                    scrollbar-width:none
                ">
                    <div className="
                    
        
                        w-4/10
                        h-20
                        rounded-r-2xl
                        border
                        border-neutral-300
                    
                    ">

                    </div>
                    <div className="
                    
                        self-end
                        w-4/10
                        h-20
                        rounded-l-2xl
                        border
                        border-neutral-300
                    
                    "></div>
                </div>
                <div className="
                
                    h-60
                    flex
                    items-center
                    w-full
                    pl-5
                    border-t
                    border-neutral-300
                    pt-3
                    
                ">
                    <textarea placeholder="Type message" className="

                        
                        border
                        border-neutral-500
                        rounded-2xl
                        h-9/10
                        flex-10
                        resize-none
                        p-2
                        focus:outline-0
                        hover:border-neutral-600
                        focus:border-neutral-600

                    "/>
                    <div className="
                    
                        flex-1
                        flex
                        items-center
                        justify-center
                        h-full
                        pr-2
                    
                    ">
                        <button className="

                            
                            border-neutral-500
                            border-2
                            rounded-full
                            w-[10vh]
                            h-[10vh]
                            flex
                            items-center
                            justify-center
                            hover:border-neutral-700
                            active:border-neutral-500
                        
                        ">
                            <img className="
                            
                                ml-3
                                p-5
                                opacity-70
                                w-full
                                h-full


                            
                            " src={send}/>
                        </button>
                    </div>
                </div>
            </div>
        </div>

    )


}

export default PchatPage