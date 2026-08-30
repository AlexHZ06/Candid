import Header from "../compnents/pheader"

function AlbumEdit(){

    return(

        <div className="
        
            w-screen 
            min-h-screen 
        
        ">
            <Header active="Gallery"/>
            <div className="
            
                w-full
                h-full
                flex
                justify-center
                mt-20
            
            ">
                <div className="
                
                    border-2
                    border-neutral-300
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    w-[100vh]
                    h-[70vh]
                    rounded-xl
                    flex
                    flex-col
                    items-center
               
                
                ">
                    <div className="
                    
                        flex
                        flex-row
                        w-[80vh]
                        justify-between
                        mt-10
                    
                    ">
                        <input placeholder="Title" type="text" className="
                        
                            border-2
                      
                            h-10
                            rounded-md
                            w-6/10
                            pl-2
                            border-neutral-500
                            hover:border-black

                            transition
                            duration-200
                          

                        
                        "/>
                        <div className="
                        
                            w-3/10
                            border-2
                       
                     
                            rounded-md
                            pl-2
                            border-neutral-500

                        
                        ">

                            

                        </div>
                    </div>
                    <textarea placeholder="Desctiption" className="
                    
                        w-[80vh]
                        border-2
                        rounded-md
                         border-neutral-500
                         hover:border-black
                        transition
                        duration-200
                        mt-5
                        h-[6vh]
                        pl-2
                        resize-none

                    
                    "/>
                    <div className="
                    
                        w-[80vh]
                        border-2
                        rounded-md
                         border-neutral-500
                         hover:border-black
                        transition
                        duration-200
                        mt-5
                        h-[40vh]
                        flex
                        items-center
                        justify-center
                        text-neutral-500
                    ">
                        Cover
                    </div>
                    <div className="
                    
                        flex
                        flex-row
                        w-[80vh]
                        items-center
                        justify-center
                        gap-10
                        mt-7
                    
                    ">
                        <button className="
                        
                            bg-neutral-700
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

                        
                        ">Submit</button>
                        <button className="
                        
                            bg-red-500
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-red-700
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black
                        
                        ">Delete</button>
                    </div>

                </div>
            </div>
        </div>

    )

}

export default AlbumEdit