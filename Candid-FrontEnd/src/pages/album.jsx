import Header from "../compnents/pheader"
import { Link } from "react-router-dom"
import TagsCatSelection from "../compnents/tagsCatSelection"

function Album(){

    return(

        <div className="
        
            w-screen 
            min-h-screen 
        
        
        ">
            <Header active="Gallery"/>
            <div className="
            
                flex
                flex-row
                h-[calc(100vh-3.75rem)]
                w-full

            
            ">

                <div className="
                
                    flex
                    flex-col
                    border-r-2
                    border-neutral-200
                    h-full
                    relative
                    w-[15vh]
                    items-center
                    pt-3
                    
                    
                
                ">
                    
                    <div className="
                    
                        flex
                        flex-col
                        gap-3
                        text-neutral-500

                        h-6/10
                        w-[15vh]
                        shrink-0
                        overflow-y-auto
                    
                    ">
                        
                        <p className="
                        
                            font-bold
                            text-black
                            pl-2
                        
                        ">Albums</p>
                        <Link className="
                        
                            hover:text-black
                            active:text-neutral-700
                            transition
                            duration-100
                            self-center
                        
                        ">AlbumName</Link>
                                                <Link className="
                        
                            hover:text-black
                            active:text-neutral-700
                            transition
                            duration-100
                            self-center
                        
                        ">AlbumName</Link>
                                                <Link className="
                        
                            hover:text-black
                            active:text-neutral-700
                            transition
                            duration-100
                            self-center
                        
                        ">AlbumName</Link>
                                                <Link className="
                        
                            hover:text-black
                            active:text-neutral-700
                            transition
                            duration-100
                            self-center
                        
                        ">AlbumName</Link>
                                                <Link className="
                        
                            hover:text-black
                            active:text-neutral-700
                            transition
                            duration-100
                            self-center
                        
                        ">AlbumName</Link>
                                                <Link className="
                        
                            hover:text-black
                            active:text-neutral-700
                            transition
                            duration-100
                            self-center
                        
                        ">AlbumName</Link>                                         
                    </div>
                    <div className="
                    
                        
                        flex
                        flex-col
                        justify-end
                        gap-5
                        h-3/10
                        mt-10
                    
                    ">

                        <Link className="
                        
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

                        
                        
                        ">Add Photo</Link>
                        <Link className="
                        
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

                        
                        
                        ">Edit Album
                        </Link>


                    </div>

                </div>
                <div className="
                
                    flex
                    w-full
                    justify-center
                    items-center

                ">
                    <div className="

                        outline    
                        w-[150vh]
                        h-9/10
                        
                    
                    
                    ">
                        <TagsCatSelection/>
                    </div>
                </div>

            </div>
        </div>

    )

}

export default Album